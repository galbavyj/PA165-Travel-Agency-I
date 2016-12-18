/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.CustomerDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.travelagencyservice.TravelAgencyPersistenceException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Inject
    private CustomerDao customerDao;

    @Override
    public void registerCustomer(Customer c, String plainTextPassword) {
        try {
            c.setPasswordHash(createHash(plainTextPassword));
            customerDao.create(c);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to create customer" + e.getMessage(), e.getCause());
        }
    }
    
    @Override
    public boolean authenticateCustomer(Customer c, String password) {
        String correctHash = c.getPasswordHash();
        if(password == null) return false;
        if(correctHash == null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    @Override
    public void updateCustomer(Customer c) {
        try {
            customerDao.update(c);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to update customer" + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void removeCustomer(Customer c) {
        try {
            customerDao.remove(c);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to remove customer" + e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return customerDao.findAllCustomers();
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Customer findByEmail(String email) {
        try {
            return customerDao.findByEmail(email);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to find customer with email " + email + e.getMessage(), e.getCause());
        }
    }

    @Override
    public Customer findById(Long customerId) {
        try {
            return customerDao.findById(customerId);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to find customer with id " + customerId + e.getMessage(), e.getCause());
        }
    }

    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }
    
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

    @Override
    public void addReservationToCustomer(Customer customer, Reservation reservation) {
        try {
            customerDao.addReservation(customer, reservation);
        }
        catch (Exception e) {
            throw new TravelAgencyPersistenceException("Failed to assign reservation " + reservation + 
                    "to customer" + customer.toString() + e.getMessage(), e.getCause());
        }
    }
}
