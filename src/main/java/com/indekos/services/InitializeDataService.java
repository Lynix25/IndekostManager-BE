package com.indekos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitializeDataService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
	public void initializeData() {
        System.out.println("=========== Start Insert Data ===========");
        /* ============================== MASTER ROLE ============================== */
        String columnList = "(`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `description`, `name`)";
        String insertPrefix = "INSERT IGNORE INTO ";
        String table = "master_role ";
        List<String> data = new ArrayList<>();
        data.add("('db77ed94-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'Pemilik Indekos', 'Owner')");
        data.add("('db781d83-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'Pengurus/ Penjaga Indekos', 'Admin')");
        data.add("('db7843f1-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'Penyewa Indekos', 'Tenant')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== MASTER SERVICE ============================== */
        columnList = "(`id`, `name`)";
        table = "master_service ";
        data = new ArrayList<>();
        data.add("(1, 'Kamar Tidur')");
        data.add("(2, 'Pembersihan Kamar')");
        data.add("(3, 'Perbaikan Fasilitas')");
        data.add("(4, 'Layanan Lainnya')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== ANNOUNCEMENT ============================== */
        columnList = "(`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `description`, `image`, `period`, `title`)";
        table = "announcement ";
        data = new ArrayList<>();
        data.add("('db791b5c-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'Book a room for a week and get a free meal voucher for the Cat\\'s Tail Tavern.', NULL, 'April 20 - May 10', 'Special Offer for Travelers')");
        data.add("('db79441a-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'Our deluxe single rooms are now fully booked, but we still have standard double rooms available for rent.', NULL, 'May 1 - May 31', 'Room Availability Update')");
        data.add("('db79727c-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'We will be conducting maintenance works from 9am to 5pm. Water and electricity supply may be temporarily interrupted during this period.', NULL, 'May 15', 'Maintenance Notice')");
        data.add("('db799a0a-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'All guests are reminded to clean up after themselves and not to make excessive noise after 10pm.', NULL, 'Ongoing', 'House Rules Reminder')");
        data.add("('db79c1c8-c91c-11ed-821e-00059a3c7a00', 'system', 1679536129000, 'system', 1679536129000, 'Book our package deal and get a discounted rate for entrance tickets to the nearby Thousand Wind Temple.', NULL, 'May 1 - June 30', 'Tourist Attraction Package')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== ROOM ============================== */
        columnList = "(`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `allotment`, `description`, `floor`, `is_deleted`, `name`, `quota`)";
        table = "room ";
        data = new ArrayList<>();
        data.add("('814bf6b7-de02-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Putra', 'Kamar khusus putra.', 1, 0, 'Kamar 101', 2)");
        data.add("('bc34fae6-de8a-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Putri', 'Kamar khusus putri.', 1, 0, 'Kamar 102', 4)");
        data.add("('c1c2624a-de8a-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Putri', 'Kamar khusus putri.', 1, 0, 'Kamar 103', 2)");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== USER ============================== */
        columnList = "(`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `alias`, `description`, `email`, `gender`, `identity_card_image`, `in_active_since`, `is_deleted`, `job`, `joined_on`, `married`, `name`, `phone`, `role_id`, `room_id`)";
        table = "user ";
        data = new ArrayList<>();

        data.add("('222e1fdd-de8c-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Eric', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'eric_leo@gmail.com', 'Laki-laki', NULL, NULL, 0, 'Penjaga kos', 1679536129, 1, 'Eric Leonard', '081023895476', 'db781d83-c91c-11ed-821e-00059a3c7a00', NULL)");
        data.add("('272ecb84-de8c-11ed-9bfd-00155dee1635', 'system', 1679536129000, '272ecb84-de8c-11ed-9bfd-00155dee1635', 1683654516919, 'Ayu', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'agung_ayu@gmail.com', 'Perempuan', NULL, NULL, 0, 'IT Specialist', 1682169318, 0, 'Agung Ayu', '081934364916', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("('3f01e38a-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Jean', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'jean_dandelion@gmail.com', 'Perempuan', NULL, NULL, 0, 'Pegawai Negeri Sipil', 1682169318, 1, 'Jean Gunnhildr', '082618361941', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("('5b4a96b4-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Mona', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'megistus0831@gmail.com', 'Perempuan', NULL, NULL, 0, 'Mahasiswi', 1682169318, 0, 'Mona Megistus', '086973745827', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("('69b50d6d-de0a-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Ferguso', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'kim_ferguso@gmail.com', 'Laki-laki', NULL, NULL, 0, 'Owner kos', 1679536129, 0, 'Kim Ferguso', '082338470167', 'db77ed94-c91c-11ed-821e-00059a3c7a00', NULL)");
        data.add("('6a1d90b6-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Faru', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'madam_faruzan@gmail.com', 'Perempuan', NULL, NULL, 0, 'Dosen Magang', 1682169318, 1, 'Faruzan', '085670408015', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("('790f5290-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Xinyan', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'rocknroll_xinyan@gmail.com', 'Perempuan', NULL, NULL, 0, 'Anggota Band', 1682169318, 0, 'Xinyan Lee', '080264341764', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("('87f02066-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Ayaka', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'ayaka_k@gmail.com', 'Perempuan', NULL, NULL, 0, 'Pegawai Swasta', 1682169318, 1, 'Ayaka Kamisato', '084622928410', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("('96d9cc2c-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Kazuha', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'kaedeharakazuha@gmail.com', 'Laki-laki', NULL, NULL, 0, 'Mahasiswa', 1682169318, 0, 'Kazuha Kaedehara', '089293714270', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("('a5b84d22-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Xiao', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'xiaoalatus@gmail.com', 'Laki-laki', NULL, NULL, 0, 'Mahasiswa', 1682169318, 0, 'Xiao Chen', '083738800966', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("('b48469a0-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Heizou', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'hei_zouu@gmail.com', 'Laki-laki', NULL, NULL, 0, 'Pegawai Negeri Sipil', 1682169318, 0, 'Heizou Shikanoin', '087035179882', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("('c365be36-df5e-11ed-9bfd-00155dee1635', 'system', 1679536129000, 'system', 1679536129000, 'Venti', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'venti_ehe@gmail.com', 'Laki-laki', NULL, NULL, 0, 'Wirausahawan', 1682169318, 0, 'Venti Barbatos', '088906438963', 'db7843f1-c91c-11ed-821e-00059a3c7a00', 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        
        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== ACCOUNT ============================== */
        columnList = "(`id`, `login_time`, `logout_time`, `password`, `username`, `user_id`)";
        table = "account ";
        data = new ArrayList<>();
        data.add("('0df852c6-de94-11ed-9bfd-00155dee1635', NULL, NULL, 'hashjean081', 'jean_d', '3f01e38a-df5e-11ed-9bfd-00155dee1635')");
        data.add("('19be6ed2-de95-11ed-9bfd-00155dee1635', NULL, NULL, 'hashshikanoin22', 'heyheizou', 'b48469a0-df5e-11ed-9bfd-00155dee1635')");
        data.add("('3da33f40-de95-11ed-9bfd-00155dee1635', NULL, NULL, 'hashventi99', 'venventi', 'c365be36-df5e-11ed-9bfd-00155dee1635')");
        data.add("('42a6f04c-de94-11ed-9bfd-00155dee1635', NULL, NULL, 'hashmona223', 'mona0831', '5b4a96b4-df5e-11ed-9bfd-00155dee1635')");
        data.add("('66e1a3e6-de94-11ed-9bfd-00155dee1635', NULL, NULL, 'hashfaruzan444', 'faruzan012', '6a1d90b6-df5e-11ed-9bfd-00155dee1635')");
        data.add("('8eae98d4-de94-11ed-9bfd-00155dee1635', NULL, NULL, 'hashxinyan1567', 'xi_nyann', '790f5290-df5e-11ed-9bfd-00155dee1635')");
        data.add("('98c69559-de89-11ed-9bfd-00155dee1635', NULL, NULL, 'hashowner', 'owner', '69b50d6d-de0a-11ed-9bfd-00155dee1635')");
        data.add("('ad58f69c-de94-11ed-9bfd-00155dee1635', NULL, NULL, 'hashayaka098', 'ayaka_k', '87f02066-df5e-11ed-9bfd-00155dee1635')");
        data.add("('d1f052aa-de94-11ed-9bfd-00155dee1635', NULL, NULL, 'hashkaedehara01', 'kazu_ha', '96d9cc2c-df5e-11ed-9bfd-00155dee1635')");
        data.add("('f3a4b4d4-de8e-11ed-9bfd-00155dee1635', 1683647793698, 1683647801221, 'hashadmin', 'admin', '222e1fdd-de8c-11ed-9bfd-00155dee1635')");
        data.add("('f5d9e22c-de94-11ed-9bfd-00155dee1635', NULL, NULL, 'hashxiao456', 'xiao1234', 'a5b84d22-df5e-11ed-9bfd-00155dee1635')");
        data.add("('f85619a0-de8e-11ed-9bfd-00155dee1635', 1683647812363, NULL, 'hashtenant', 'tenant', '272ecb84-de8c-11ed-9bfd-00155dee1635')");
        
        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== CONTACT ABLE PERSON ============================== */
        columnList = "(`id`, `address`, `is_deleted`, `name`, `phone`, `relation`, `user_id`)";
        table = "contact_able_person ";
        data = new ArrayList<>();
        data.add("('0b02df7b-de8a-11ed-9bfd-00155dee1635', 'Komplek Taman Lama, Tangerang Selatan, Banten', 0, 'Bapak Agung', '081243278240', 'Ayah kandung', '272ecb84-de8c-11ed-9bfd-00155dee1635')");
        data.add("('0b75f24c-de8a-11ed-9bfd-00155dee1635', 'Rumah Keluarga Gunnhildr, kawasan Jalan Luhur, Mondstadt', 0, 'Barbara', '081138735348', 'Adik kandung', '3f01e38a-df5e-11ed-9bfd-00155dee1635')");
        data.add("('1f1ab68c-de90-11ed-9bfd-00155dee1635', 'Villa Kaltsit, kawasan Bukit Salju, Mondstadt', 0, 'Layla', '089277176534', 'Adik Tiri', '5b4a96b4-df5e-11ed-9bfd-00155dee1635')");
        data.add("('2a1b7c2e-de93-11ed-9bfd-00155dee1635', 'Jalan Yae, Pelabuhan Ritou, Inazuma', 0, 'Ibu Beidou', '083615847723', 'Ibu Tiri', '96d9cc2c-df5e-11ed-9bfd-00155dee1635')");
        data.add("('3d1e2b4e-de95-11ed-9bfd-00155dee1635', 'Jalan Tenryou, Inazuma City, Inazuma', 0, 'Bapak Shikanoin', '083817738981', 'Ayah Kandung', 'b48469a0-df5e-11ed-9bfd-00155dee1635')");
        data.add("('6b53ee9e-de94-11ed-9bfd-00155dee1635', 'Jalan Panjang Yujing, Pedesaan Mingyun, Liyue', 0, 'Ibu Ningguang', '081649637365', 'Ibu Tiri', 'a5b84d22-df5e-11ed-9bfd-00155dee1635')");
        data.add("('6b6c3e9b-de8f-11ed-9bfd-00155dee1635', 'Komplek Taman Lama, Tangerang Selatan, Banten', 0, 'Ibu Agung', '0812528463487', 'Ibu kandung', '272ecb84-de8c-11ed-9bfd-00155dee1635')");
        data.add("('7e06d70e-de90-11ed-9bfd-00155dee1635', 'Komplek Raya Sumeru, Sumeru City, Sumeru', 0, 'Ibu Nahida', '087721042886', 'Bibi kandung', '6a1d90b6-df5e-11ed-9bfd-00155dee1635')");
        data.add("('8bc6dbbe-de91-11ed-9bfd-00155dee1635', 'Jalan Kuno Wanmin, Pelabuhan Liyue, Liyue', 0, 'Xiangling', '084895971275', 'Sepupu kandung', '790f5290-df5e-11ed-9bfd-00155dee1635')");
        data.add("('9a32a11c-de8f-11ed-9bfd-00155dee1635', 'Villa Kaltsit, kawasan Bukit Salju, Mondstadt', 0, 'Ibu Barbeloth', '081828395713', 'Ibu Tiri', '5b4a96b4-df5e-11ed-9bfd-00155dee1635')");
        data.add("('ae974cde-de95-11ed-9bfd-00155dee1635', 'Apartemen Flamingo, Jalan Angin Laut, Mondstadt', 0, 'Bapak Dvalin', '085034229450', 'Ayah Kandung', 'b48469a0-df5e-11ed-9bfd-00155dee1635')");
        data.add("('dc49f14e-de91-11ed-9bfd-00155dee1635', 'Jalan Kuno Wanmin, Pelabuhan Liyue, Liyue', 0, 'Bapak Mao', '080822315438', 'Paman kandung', '790f5290-df5e-11ed-9bfd-00155dee1635')");
        data.add("('eb85838e-de92-11ed-9bfd-00155dee1635', 'Kediaman Kamisato, Narukami, Inazuma', 0, 'Ayato Kamisato', '089298526519', 'Kakak Kandung', '87f02066-df5e-11ed-9bfd-00155dee1635')");
        data.add("('f08d9aee-de93-11ed-9bfd-00155dee1635', 'Jalan Panjang Yujing, Pedesaan Mingyun, Liyue', 0, 'Bapak Zhongli', '085791451012', 'Bapak Kandung', 'a5b84d22-df5e-11ed-9bfd-00155dee1635')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== ROOM DETAIL ============================== */
        columnList = "(`id`, `description`, `name`, `detail_category_id`, `room_id`)";
        table = "room_detail ";
        data = new ArrayList<>();
        data.add("(1, '4 x 5 meter', 'Luas Ruangan', 1, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(2, '800 watt', 'Listrik', 1, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(3, 'Single bed 2 unit', 'Kasur', 1, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(4, '2 buah', 'Bantal', 1, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(5, '2 buah', 'Guling', 1, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(6, 'Di dalam kamar', 'Kamar Mandi Dalam', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(7, NULL, 'Kloset Jongkok', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(8, NULL, 'Kloset Duduk', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(9, NULL, 'Kran', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(10, 'Shower panjang dan pendek', 'Shower', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(11, 'Tersedia', 'Air Panas', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(12, NULL, 'Bak Mandi', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(13, '1 buah', 'Wastafel', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(14, '1 buah', 'Cermin', 2, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(15, '2 unit', 'Lemari Baju', 3, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(16, '2 unit', 'Meja', 3, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(17, '2 unit', 'Kursi', 3, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(18, 'Kecepatan 20mbps', 'WiFi', 4, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(19, '1 unit', 'AC', 4, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(20, NULL, 'Kipas Angin', 4, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(21, '10 x 12 meter', 'Luas Ruangan', 1, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(22, '1500 watt', 'Listrik', 1, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(23, 'Single bed 4 unit', 'Kasur', 1, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(24, '4 buah', 'Bantal', 1, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(25, '4 buah', 'Guling', 1, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(26, 'Di dalam kamar', 'Kamar Mandi Dalam', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(27, NULL, 'Kloset Jongkok', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(28, NULL, 'Kloset Duduk', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(29, NULL, 'Kran', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(30, 'Shower panjang dan pendek', 'Shower', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(31, 'Tersedia', 'Air Panas', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(32, NULL, 'Bak Mandi', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(33, '2 buah', 'Wastafel', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(34, '2 buah', 'Cermin', 2, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(35, '4 unit', 'Lemari Baju', 3, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(36, '4 unit', 'Meja', 3, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(37, '4 unit', 'Kursi', 3, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(38, 'Kecepatan 20mbps', 'WiFi', 4, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(39, '1 unit', 'AC', 4, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(40, NULL, 'Kipas Angin', 4, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(41, '4 x 5 meter', 'Luas Ruangan', 1, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(42, '800 watt', 'Listrik', 1, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(43, 'Single bed 2 unit', 'Kasur', 1, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(44, '2 buah', 'Bantal', 1, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(45, '2 buah', 'Guling', 1, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(46, 'Di dalam kamar', 'Kamar Mandi Dalam', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(47, NULL, 'Kloset Jongkok', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(48, NULL, 'Kloset Duduk', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(49, NULL, 'Kran', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(50, 'Shower panjang dan pendek', 'Shower', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(51, 'Tersedia', 'Air Panas', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(52, NULL, 'Bak Mandi', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(53, '1 buah', 'Wastafel', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(54, '1 buah', 'Cermin', 2, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(55, '2 unit', 'Lemari Baju', 3, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(56, '2 unit', 'Meja', 3, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(57, '2 unit', 'Kursi', 3, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(58, 'Kecepatan 20mbps', 'WiFi', 4, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(59, '1 unit', 'AC', 4, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(60, NULL, 'Kipas Angin', 4, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== MASTER ROOM DETAIL CATEGORY ============================== */
        columnList = "(`id`, `name`)";
        table = "master_room_detail_category ";
        data = new ArrayList<>();
        data.add("(1, 'Kamar Tidur')");
        data.add("(2, 'Kamar Mandi')");
        data.add("(3, 'Furniture')");
        data.add("(4, 'Alat Elektronik')");
        data.add("(5, 'Fasilitas Kamar Lainnya')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== ROOM PRICE DETAIL ============================== */
        columnList = "(`id`, `capacity`, `price`, `room_id`)";
        table = "room_price_detail ";
        data = new ArrayList<>();
        data.add("(1, 1, 2500000, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(2, 2, 1500000, '814bf6b7-de02-11ed-9bfd-00155dee1635')");
        data.add("(3, 1, 4000000, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(4, 2, 2500000, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(5, 4, 1250000, 'bc34fae6-de8a-11ed-9bfd-00155dee1635')");
        data.add("(6, 1, 2500000, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        data.add("(7, 2, 1500000, 'c1c2624a-de8a-11ed-9bfd-00155dee1635')");
        
        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== SERVICE ============================== */
        columnList = "(`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `due_date`, `penalty`, `price`, `quantity`, `service_name`, `units`, `variant`)";
        table = "service ";
        data = new ArrayList<>();
        data.add("('2e1ba50e-18bf-4982-a519-79ae958a5103', 'admin', 1680095957304, 'admin', 1680095957304, 0, 0, 0, 0, 'Pembersihan Kamar', NULL, 'Layanan Kamar')");
        data.add("('5876fa23-5e91-4be7-bf76-7245c48d631e', '813cad20-4f19-4ce9-a325-f7e00999118c', 1682477559405, '813cad20-4f19-4ce9-a325-f7e00999118c', 1682477559405, 0, 0, 0, 0, 'Perbaikan Fasilitas', NULL, 'AC')");
        data.add("('5cedcfc0-8456-431c-8368-c8f9e0ea0a82', 'admin', 1680095909837, 'admin', 1680095909837, 0, 1, 3000, 0, 'Laundry', 'buah', 'Baju')");
        data.add("('729ed33e-3f40-43b9-be26-57cd914ab1f0', 'admin', 1680095920709, 'admin', 1680095920709, 0, 2, 15000, 0, 'Laundry', 'buah', 'Selimut')");
        data.add("('93b3df97-3154-4a29-ab00-70cb157a8541', '813cad20-4f19-4ce9-a325-f7e00999118c', 1682477495888, '813cad20-4f19-4ce9-a325-f7e00999118c', 1682477495888, 5, 0, 250000, 0, 'Laundry', 'item', 'Kasur')");
        data.add("('a958bb45-8f06-4f3c-a13a-0672c6a347a3', '16c0cd7a-7be7-4027-80b5-8029ef064d29', 1682485250121, '16c0cd7a-7be7-4027-80b5-8029ef064d29', 1682485250121, 0, 0, 0, 0, 'Perbaikan Fasilitas', 'item', 'Penghangat air')");
        data.add("('b3a95d99-8890-4a8e-bbf2-942e16bcc71c', 'admin', 1680095931135, 'admin', 1680095931135, 3, 0, 30000, 0, 'Laundry', 'pasang', 'Sepatu')");
        data.add("('d3ea6c21-beba-41c7-8439-b547a43e579b', '813cad20-4f19-4ce9-a325-f7e00999118c', 1682477647169, '813cad20-4f19-4ce9-a325-f7e00999118c', 1682477647169, 0, 0, 0, 0, 'Perbaikan Fasilitas', 'item', 'Exhaust')");
        data.add("('e2e293f1-e3e4-11ed-a584-00155ddd50f3', 'system', 1683648586000, 'system', 1683648586000, 0, 0, 0, 0, 'Pembersihan Kamar', NULL, 'Lainnya')");
        data.add("('e2e295f3-e3e4-11ed-a584-00155ddd50f3', 'system', 1683646619000, 'system', 1683646619000, 0, 0, 0, 0, 'Pembersihan Kamar', NULL, 'Lainnya')");
        data.add("('e2e305f3-e3e4-11ed-a584-00155ddd50f3', 'system', 1683646619000, 'system', 1683646619000, 0, 0, 0, 0, 'Perbaikan Fasilitas', NULL, 'Lainnya')");
        data.add("('e2e315f3-e3e4-11ed-a584-00155ddd50f3', 'system', 1683646619000, 'system', 1683646619000, 0, 0, 0, 0, 'Layanan Lainnya', NULL, 'Lainnya')");
        data.add("('e2e335f3-e3e4-11ed-a584-00155ddd50f3', 'system', 1683646619000, 'system', 1683646619000, 0, 0, 0, 0, 'Layanan Lainnya', NULL, 'Pindah Kamar')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }

        /* ============================== USER DOCUMENT ============================== */
//        columnList = "";
//        table = " ";
//        data = new ArrayList<>();
//
//        for (String query: data) {
//            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
//        }

        /* ============================== USER SETTING ============================== */
        columnList = "(`id`, `enable_notification`, `share_room`, `user_id`)";
        table = "user_setting ";
        data = new ArrayList<>();
        data.add("(1, 0, 0, 'c365be36-df5e-11ed-9bfd-00155dee1635')");
        data.add("(2, 0, 0, 'b48469a0-df5e-11ed-9bfd-00155dee1635')");
        data.add("(3, 0, 0, 'a5b84d22-df5e-11ed-9bfd-00155dee1635')");
        data.add("(4, 0, 0, '96d9cc2c-df5e-11ed-9bfd-00155dee1635')");
        data.add("(5, 0, 0, '87f02066-df5e-11ed-9bfd-00155dee1635')");
        data.add("(6, 0, 0, '790f5290-df5e-11ed-9bfd-00155dee1635')");
        data.add("(7, 0, 0, '6a1d90b6-df5e-11ed-9bfd-00155dee1635')");
        data.add("(8, 0, 0, '69b50d6d-de0a-11ed-9bfd-00155dee1635')");
        data.add("(9, 0, 0, '5b4a96b4-df5e-11ed-9bfd-00155dee1635')");
        data.add("(10, 0, 0, '3f01e38a-df5e-11ed-9bfd-00155dee1635')");
        data.add("(11, 1, 0, '272ecb84-de8c-11ed-9bfd-00155dee1635')");
        data.add("(12, 0, 0, '222e1fdd-de8c-11ed-9bfd-00155dee1635')");

        for (String query: data) {
            jdbcTemplate.update(insertPrefix + table + columnList + "VALUES " + query);
        }
        System.out.println("=========== Stop Insert Data ===========");
    }

    void insertToDb(String tableName){

    }
}
