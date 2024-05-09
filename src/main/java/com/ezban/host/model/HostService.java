package com.ezban.host.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HostService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HostMailService hostMailService;

    private static final int PASSWORD_LENGTH = 10;

    @Transactional(rollbackFor = Exception.class)
    public Host registerHost(String hostAccount, String hostName, String hostMail, String hostPhone) {
        // Check unique constraints
        if (hostRepository.findByHostAccount(hostAccount).isPresent()) {
            throw new DataIntegrityViolationException("帳號已有人使用");
        }
        if (hostRepository.findByHostMail(hostMail).isPresent()) {
            throw new DataIntegrityViolationException("信箱已有人使用");
        }
        if (hostRepository.findByHostName(hostName).isPresent()) {
            throw new DataIntegrityViolationException("名稱已有人使用");
        }
        if (hostRepository.findByHostPhone(hostPhone).isPresent()) {
            throw new DataIntegrityViolationException("電話已有人使用");
        }

        Host host = new Host();
        host.setHostAccount(hostAccount);
        host.setHostName(hostName);
        host.setHostMail(hostMail);
        host.setHostPhone(hostPhone);
        host.setHostStatus((byte) 1);

        // Generate and set initial password
        String rawPassword = HostPassRandom.generatePassword(PASSWORD_LENGTH);
        host.setHostPwd(passwordEncoder.encode(rawPassword));
        hostRepository.save(host);

        // Send email with initial password
        String message = "Your initial password is: " + rawPassword + ". Please change it upon your first login for security.";
        hostMailService.sendEmail(host.getHostMail(), "Welcome to Ezban", message);

        return host;
    }

    @Transactional(rollbackFor = Exception.class)
    public void setupHostPassword(int hostNo) {
        Optional<Host> optionalHost = hostRepository.findById(hostNo);
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
            String rawPassword = HostPassRandom.generatePassword(PASSWORD_LENGTH);
            String encryptedPassword = passwordEncoder.encode(rawPassword);
            host.setHostPwd(encryptedPassword);
            hostRepository.save(host);

            hostMailService.sendEmail(host.getHostMail(), "Welcome to Ezban",
                    "Your initial password is: " + rawPassword + ". Please change it upon your first login for security.");
        } else {
            throw new RuntimeException("Host not found with ID: " + hostNo);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateHostPassword(int hostNo, String currentPassword, String rawNewPassword) {
        Optional<Host> optionalHost = hostRepository.findById(hostNo);
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
            // Verify current password
            if (!passwordEncoder.matches(currentPassword, host.getHostPwd())) {
                throw new RuntimeException("Current password is incorrect");
            }

            String encryptedPassword = passwordEncoder.encode(rawNewPassword);
            host.setHostPwd(encryptedPassword);
            hostRepository.save(host);
            hostMailService.sendEmail(host.getHostMail(), "Password Update", "Your password has been successfully updated.");
        } else {
            throw new RuntimeException("No host found with the specified ID: " + hostNo);
        }
    }

    public boolean authenticate(String hostAccount, String rawPassword) {
        Optional<Host> optionalHost = hostRepository.findByHostAccount(hostAccount);
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
            return passwordEncoder.matches(rawPassword, host.getHostPwd());
        } else {
            return false;
        }
    }
}
