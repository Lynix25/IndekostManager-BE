package com.indekos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indekos.model.User;
import com.indekos.model.UserSetting;

public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {
	UserSetting findByUser (User user);
}
