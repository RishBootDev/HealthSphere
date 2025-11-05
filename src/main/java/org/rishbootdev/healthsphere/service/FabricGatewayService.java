package org.rishbootdev.healthsphere.service;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import jakarta.annotation.PreDestroy;
import org.hyperledger.fabric.client.*;
import org.hyperledger.fabric.client.identity.*;
import org.rishbootdev.healthsphere.exception.NetworkConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class FabricGatewayService {

    @Value("${fabric.certificate-path}")
    private String certificatePath;

    @Value("${fabric.private-key-dir}")
    private String privateKeyDir;

    @Value("${fabric.msp-id:Org1MSP}")
    private String mspId;

    @Value("${fabric.peer-endpoint:localhost:7051}")
    private String peerEndpoint;

    @Value("${fabric.tls-cert-path}")
    private String tlsCertPath;

    @Value("${fabric.channel:healthchannel}")
    private String channelName;

    @Value("${fabric.chaincode:healthsphere}")
    private String chaincodeName;

    private Gateway gateway;
    private ManagedChannel channel;

    public synchronized Contract getContract() {
        return getContract(chaincodeName, null);
    }

    public synchronized Contract getContract(String contractName){
        return getContract(chaincodeName,contractName);
    }

    public synchronized Contract getContract(String chaincodeName, String contractName) {
        try {
            if (gateway == null) {
                Path certPath = Paths.get(certificatePath);
                X509Certificate certificate = Identities.readX509Certificate(Files.newBufferedReader(certPath));

                Path keyDir = Paths.get(privateKeyDir);
                Path keyPath;
                try (Stream<Path> list = Files.list(keyDir)) {
                    keyPath = list
                            .filter(p -> {
                                String n = p.getFileName().toString();
                                return n.endsWith("_sk") || n.endsWith(".pem") || n.endsWith(".key");
                            })
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("No private key found in " + privateKeyDir));
                }

                PrivateKey privateKey = Identities.readPrivateKey(Files.newBufferedReader(keyPath));
                Identity identity = new X509Identity(mspId, certificate);
                Signer signer = Signers.newPrivateKeySigner(privateKey);

                File tlsCertFile = new File(tlsCertPath);
                String[] parts = peerEndpoint.split(":", 2);
                String host = parts[0];
                int port = Integer.parseInt(parts[1]);

                channel = NettyChannelBuilder.forAddress(host, port)
                        .sslContext(GrpcSslContexts.forClient().trustManager(tlsCertFile).build())
                        .overrideAuthority("peer0.org1.example.com")
                        .build();

                gateway = Gateway.newInstance()
                        .identity(identity)
                        .signer(signer)
                        .connection(channel)
                        .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                        .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                        .submitOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                        .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES))
                        .connect();
            }

            Network network = gateway.getNetwork(channelName);

            if (contractName != null && !contractName.isEmpty()) {
                return network.getContract(chaincodeName, contractName);
            } else {
                return network.getContract(chaincodeName);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new NetworkConnectionException("Unable to connect to the Fabric network: " + e.getMessage());
        }
    }

    @PreDestroy
    public void close() {
        try {
            if (gateway != null) gateway.close();
            if (channel != null) channel.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
