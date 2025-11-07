package org.rishbootdev.healthsphere.service;

import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.rishbootdev.healthsphere.exception.ChainCodeException;
import org.rishbootdev.healthsphere.exception.LedgerAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final FabricGatewayService fabricGatewayService;

    private Contract getTestContract() throws Exception {
        return fabricGatewayService.getContract( "TestContract");
    }

    public String ping() {
        try {
            Contract contract = getTestContract();
            byte[] result = contract.evaluateTransaction("ping");
            return new String(result);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to ping chaincode: " + e.getMessage());
        }
    }

    public String healthCheck() {
        try {
            Contract contract = getTestContract();
            byte[] result = contract.evaluateTransaction("healthCheck");
            return new String(result);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to check health: " + e.getMessage());
        }
    }

    public String createRecordTest(String key, String value) {
        try {
            Contract contract = getTestContract();
            contract.submitTransaction("createRecordTest", key, value);
            return "Record created successfully with key: " + key;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to create test record: " + e.getMessage());
        }
    }

    public String queryRecordTest(String key) {
        try {
            Contract contract = getTestContract();
            byte[] result = contract.evaluateTransaction("queryRecordTest", key);
            return new String(result);
        } catch (Exception e) {
            throw new LedgerAccessException("Failed to query test record: " + e.getMessage());
        }
    }

    public String deleteRecordTest(String key) {
        try {
            Contract contract = getTestContract();
            contract.submitTransaction("deleteRecordTest", key);
            return "Record deleted successfully with key: " + key;
        } catch (Exception e) {
            throw new ChainCodeException("Failed to delete test record: " + e.getMessage());
        }
    }

    public String testLedgerData() {
        try {
            Contract contract = getTestContract();
            byte[] result = contract.submitTransaction("testLedgerData");
            return new String(result);
        } catch (Exception e) {
            throw new ChainCodeException("Failed to load test ledger data: " + e.getMessage());
        }
    }
}
