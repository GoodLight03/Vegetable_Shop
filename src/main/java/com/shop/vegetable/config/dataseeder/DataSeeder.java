package com.shop.vegetable.config.dataseeder;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.shop.vegetable.entity.Role;
import com.shop.vegetable.entity.Users;
import com.shop.vegetable.service.RoleService;
import com.shop.vegetable.service.UserService;


@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	// @Autowired
	// private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// vai trof
		if (roleService.findByNameRole("ADMIN") == null) {
			roleService.save(new Role("ADMIN"));
		}

		if (roleService.findByNameRole("CUSTOMER") == null) {
			roleService.save(new Role("CUSTOMER"));
		}
		
		if (roleService.findByNameRole("SHIPPER") == null) {
			roleService.save(new Role("SHIPPER"));
		}

		// Admin account
		if (userService.findByUsername("Admin") == null) {
			userService.saveAD();
		}

		// // Member account
		// if (userRepository.findByEmail("member@gmail.com") == null) {
		// 	NguoiDung member = new NguoiDung();
		// 	member.setEmail("member@gmail.com");
		// 	member.setPassword(passwordEncoder.encode("123456"));
		// 	HashSet<VaiTro> roles = new HashSet<>();
		// 	roles.add(roleRepository.findByTenVaiTro("ROLE_MEMBER"));
		// 	member.setVaiTro(roles);
		// 	userRepository.save(member);
		// }
		
		// // Shipper account
		// if (userRepository.findByEmail("shipper@gmail.com") == null) {
		// 	NguoiDung member = new NguoiDung();
		// 	member.setEmail("shipper@gmail.com");
		// 	member.setPassword(passwordEncoder.encode("123456"));
		// 	HashSet<VaiTro> roles = new HashSet<>();
		// 	roles.add(roleRepository.findByTenVaiTro("ROLE_SHIPPER"));
		// 	member.setVaiTro(roles);
		// 	userRepository.save(member);
		// }
	}
}
