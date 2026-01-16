package dev.andregurgel.fsm_api.service;

import dev.andregurgel.fsm_api.model.KeyPairEntity;
import dev.andregurgel.fsm_api.repository.KeyPairEntityRepository;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyPairService {

    private final KeyPairEntityRepository keyPairEntityRepository;

    public KeyPairService(KeyPairEntityRepository keyPairEntityRepository) {
        this.keyPairEntityRepository = keyPairEntityRepository;
    }

    public RSAPublicKey getPublicKey() {
        KeyPairEntity entity = getOrCreateKeyPair();
        byte[] publicKeyBytes = Base64.getDecoder().decode(entity.getPublicKey());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    public RSAPrivateKey getPrivateKey() {
        KeyPairEntity entity = getOrCreateKeyPair();
        byte[] privateKeyBytes = Base64.getDecoder().decode(entity.getPrivateKey());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }

    private KeyPairEntity getOrCreateKeyPair() {
        return keyPairEntityRepository.findTopByOrderByCreatedAtDesc()
                .orElseGet(this::createAndSaveKeyPair);
    }

    private KeyPairEntity createAndSaveKeyPair() {
        KeyPair keyPair = generateKeyPair();

        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        KeyPairEntity entity = new KeyPairEntity();
        entity.setPublicKey(publicKey);
        entity.setPrivateKey(privateKey);

        return keyPairEntityRepository.save(entity);
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
