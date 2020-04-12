package com.zh.gateway.authentication.auth;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static org.springframework.security.crypto.util.EncodingUtils.concatenate;
import static org.springframework.security.crypto.util.EncodingUtils.subArray;

public class PasswordEncoderImpl implements PasswordEncoder {
  private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
  private static final int DEFAULT_SALT_SIZE = 128 / 8;
  private static final int DEFAULT_HASH_WIDTH = 256 / 8;
  private static final int DEFAULT_ITERATIONS = 10000;
  private static final int KEY_DERIVATION_PRF = 1;
  private static final int DEFAULT_VERSION = 0x01;

  private final BytesKeyGenerator saltGenerator = KeyGenerators.secureRandom(DEFAULT_SALT_SIZE);

  private final int hashWidth;
  private final int iterations;


  public PasswordEncoderImpl() {
    this.iterations = DEFAULT_ITERATIONS;
    this.hashWidth = DEFAULT_HASH_WIDTH;
  }

  private static int readNetworkByteOrder(byte[] buffer, int offset) {
    return (buffer[offset + 0] << 24)
        | (buffer[offset + 1] << 16)
        | (buffer[offset + 2] << 8)
        | (buffer[offset + 3]);
  }

  private static void writeNetworkByteOrder(byte[] buffer, int offset, int value) {
    if (value < 0) {
      throw new IllegalArgumentException("value should an unsigned integer");
    }

    buffer[offset + 0] = (byte) (value >> 24);
    buffer[offset + 1] = (byte) (value >> 16);
    buffer[offset + 2] = (byte) (value >> 8);
    buffer[offset + 3] = (byte) (value >> 0);
  }

  private byte[] encode(CharSequence rawPassword, byte[] salt, int iterations, int hashWidth) {
    try {
      PBEKeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(), salt, iterations, hashWidth * 8);
      SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
      return skf.generateSecret(spec).getEncoded();
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException("Could not create hash", e);
    }
  }

  @Override
  public String encode(CharSequence rawPassword) {
    byte[] header = new byte[13];
    header[0] = DEFAULT_VERSION;
    writeNetworkByteOrder(header, 1, KEY_DERIVATION_PRF);
    writeNetworkByteOrder(header, 5, iterations);
    writeNetworkByteOrder(header, 9, DEFAULT_SALT_SIZE);

    byte[] salt = this.saltGenerator.generateKey();
    byte[] encoded = encode(rawPassword, salt, iterations, hashWidth);
    return new String(Base64.encode(concatenate(header, salt, encoded)), StandardCharsets.UTF_8);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    byte[] digested = Base64.decode(encodedPassword.getBytes());
    if (digested[0] == DEFAULT_VERSION) {
      int keyDerivationPrf = readNetworkByteOrder(digested, 1);
      int iterations = readNetworkByteOrder(digested, 5);
      int saltLength = readNetworkByteOrder(digested, 9);

      if (keyDerivationPrf != KEY_DERIVATION_PRF || saltLength < 128 / 8) {
        return false;
      }

      byte[] salt = subArray(digested, 13, 13 + saltLength);
      byte[] subKey = subArray(digested, 13 + saltLength, digested.length);
      return Arrays.equals(subKey, encode(rawPassword, salt, iterations, subKey.length));
    } else {
      return false;
    }
  }
}
