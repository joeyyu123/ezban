package com.ezban.host.model;

import com.ezban.eventcoupon.model.EventCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HostService {

	@Autowired
	private HostRepository hostRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HostMailService hostMailService;

	private static final int PASSWORD_LENGTH = 10;

//    @Transactional(rollbackFor = Exception.class)
//    public Host registerHost(String hostAccount, String hostName, String hostMail, String hostPhone) {
//        // Check unique constraints
//        if (hostRepository.findByHostAccount(hostAccount).isPresent()) {
//            throw new DataIntegrityViolationException("帳號已有人使用");
//        }
//        if (hostRepository.findByHostMail(hostMail).isPresent()) {
//            throw new DataIntegrityViolationException("信箱已有人使用");
//        }
//        if (hostRepository.findByHostName(hostName).isPresent()) {
//            throw new DataIntegrityViolationException("名稱已有人使用");
//        }
//        if (hostRepository.findByHostPhone(hostPhone).isPresent()) {
//            throw new DataIntegrityViolationException("電話已有人使用");
//        }
//
//        Host host = new Host();
//        host.setHostAccount(hostAccount);
//        host.setHostName(hostName);
//        host.setHostMail(hostMail);
//        host.setHostPhone(hostPhone);
//        host.setHostStatus((byte) 1);
//
//        // Generate and set initial password
//        String rawPassword = HostPassRandom.generatePassword(PASSWORD_LENGTH);
//        host.setHostPwd(passwordEncoder.encode(rawPassword));
//        hostRepository.save(host);
//
//        // Send email with initial password
//        String message = "Your initial password is: " + rawPassword + ". Please change it upon your first login for security.";
//        hostMailService.sendEmail(host.getHostMail(), "Welcome to Ezban", message);
//
//        return host;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void setupHostPassword(int hostNo) {
//        Optional<Host> optionalHost = hostRepository.findById(hostNo);
//        if (optionalHost.isPresent()) {
//            Host host = optionalHost.get();
//            String rawPassword = HostPassRandom.generatePassword(PASSWORD_LENGTH);
//            String encryptedPassword = passwordEncoder.encode(rawPassword);
//            host.setHostPwd(encryptedPassword);
//            hostRepository.save(host);
//
//            hostMailService.sendEmail(host.getHostMail(), "Welcome to Ezban",
//                    "Your initial password is: " + rawPassword + ". Please change it upon your first login for security.");
//        } else {
//            throw new RuntimeException("Host not found with ID: " + hostNo);
//        }
//    }
	// ==================================下面這段是未加密======================================
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
		// host.setHostPwd(passwordEncoder.encode(rawPassword)); // 加密邏輯已註解
		host.setHostPwd(rawPassword); // 直接使用未加密密碼
		hostRepository.save(host);

		// Send email with initial password
		String message = "Your initial password is: " + rawPassword
				+ ". Please change it upon your first login for security.";
		hostMailService.sendEmail(host.getHostMail(), "Welcome to Ezban", message);

		return host;
	}

	@Transactional(rollbackFor = Exception.class)
	public void setupHostPassword(int hostNo) {
		Optional<Host> optionalHost = hostRepository.findById(hostNo);
		if (optionalHost.isPresent()) {
			Host host = optionalHost.get();
			String rawPassword = HostPassRandom.generatePassword(PASSWORD_LENGTH);
			// String encryptedPassword = passwordEncoder.encode(rawPassword); // 加密邏輯已註解
			host.setHostPwd(rawPassword); // 直接使用未加密密碼
			hostRepository.save(host);

			hostMailService.sendEmail(host.getHostMail(), "Welcome to Ezban", "Your initial password is: " + rawPassword
					+ ". Please change it upon your first login for security.");
		} else {
			throw new RuntimeException("Host not found with ID: " + hostNo);
		}
	}

	// ====================================未加密==================================

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
			hostMailService.sendEmail(host.getHostMail(), "Password Update",
					"Your password has been successfully updated.");
		} else {
			throw new RuntimeException("No host found with the specified ID: " + hostNo);
		}
	}

	public boolean authenticate(String hostAccount, String rawPassword) {
		Optional<Host> optionalHost = hostRepository.findByHostAccount(hostAccount);
		if (optionalHost.isPresent()) {
			Host host = optionalHost.get();
// 驗證密碼是否加密 return passwordEncoder.matches(rawPassword, host.getHostPwd());

			// 直接使用明文比较
			return rawPassword.equals(host.getHostPwd());
		} else {
			return false;
		}
	}

	public Host login(String account, String password) {
		return hostRepository.findByHostAccount(account)
// 密碼加密               .filter(host -> passwordEncoder.matches(password, host.getHostPwd()))

				.filter(host -> host.getHostPwd().equals(password))// 普通比較
				.orElse(null);
	}

	public boolean changePassword(String username, String oldPassword, String newPassword) {
		Optional<Host> hostOptional = hostRepository.findByHostAccount(username);
		if (hostOptional.isPresent()) {
			Host host = hostOptional.get();
//加密功能          if (passwordEncoder.matches(oldPassword, host.getHostPwd())) {
//                host.setHostPwd(passwordEncoder.encode(newPassword));

			if (oldPassword.equals(host.getHostPwd())) { //普通比較
				host.setHostPwd(newPassword);            //普通比較
				
				hostRepository.save(host);
				return true;
			}
		}
		return false;
	}

	public Optional<Host> findHostByAccount(String account) {
		return hostRepository.findByHostAccount(account);
	}
	public Optional<Host> findHostByHostNo(String hostNo) {
		return hostRepository.findHostByHostNo(Integer.valueOf(hostNo));
	}

	public void saveHost(Host host) {
		hostRepository.save(host);
	}

	public Host getOneHost(Integer hostNo) {
		Optional<Host> optional = hostRepository.findById(hostNo);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<Host> getAll() {
		return hostRepository.findAll();
	}

	public Set<EventCoupon> getEventCouponsByHostNo(Integer hostNo) {
		return getOneHost(hostNo).getEventCoupons();
	}

	public Host getHostByEventNo(Integer eventNo) {
		return hostRepository.findHostByEventNo(eventNo)
				.orElseThrow(() -> new RuntimeException("Host not found"));
	}

	public String getHostNameByEventNo(Integer eventNo) {
		return hostRepository.findHostNameByEventNo(eventNo);
	}
	public Host findByHostName(String hostName) {
		return hostRepository.findByHostName(hostName).orElse(null);
	}
}