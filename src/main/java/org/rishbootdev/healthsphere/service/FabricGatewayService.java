package org.rishbootdev.healthsphere.service;

import io.grpc.ManagedChannel;
import org.hyperledger.fabric.client.*;
import org.hyperledger.fabric.client.identity.*;
import org.rishbootdev.healthsphere.exception.NetworkConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@Service
public class FabricGatewayService {

    @Value("${fabric.certificate-path:./msp/signcerts/cert.pem}")
    private String certificatePath;

    @Value("${fabric.private-key-path:./msp/keystore/priv_sk}")
    private String privateKeyPath;

    @Value("${fabric.msp-id:Org1MSP}")
    private String mspId;

    @Value("${fabric.connection-profile:./connection-org1.json}")
    private String networkConfigPath;

    @Value("${fabric.peer-endpoint:localhost:7051}")
    private String peerEndpoint;

    @Value("${fabric.channel:healthchannel}")
    private String channelName;

    @Value("${fabric.chaincode:healthspherecc}")
    private String chaincodeName;

    public Contract getContract() {
        try {

            Path certPath = Paths.get(certificatePath);
            Path keyPath = Paths.get(privateKeyPath);

            X509Certificate certificate = Identities.readX509Certificate(Files.newBufferedReader(certPath));
            PrivateKey privateKey = Identities.readPrivateKey(Files.newBufferedReader(keyPath));

            Identity identity = new X509Identity(mspId, certificate);
            Signer signer = Signers.newPrivateKeySigner(privateKey);

            ManagedChannel channel = io.grpc.ManagedChannelBuilder
                    .forTarget(peerEndpoint)
                    .usePlaintext()
                    .build();


            Gateway gateway = Gateway.newInstance()
                    .identity(identity)
                    .signer(signer)
                    .connection(channel)
                    .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                    .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                    .submitOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                    .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES))
                    .connect();

            Network network = gateway.getNetwork(channelName);
            return network.getContract(chaincodeName);

        } catch (Exception e) {
            throw new NetworkConnectionException("unable to connect the fiber network");
        }
    }
}
