package com.example.my_app;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_app.DTO.Products.ProductsBrandDTO;
import com.example.my_app.DTO.Products.ProductsCategoryDTO;
import com.example.my_app.DTO.Products.ProductsDTO;
import com.example.my_app.DTO.Products.ProductsImgDTO;
import com.example.my_app.DTO.Products.ProductsSupportAttributeDTO;
import com.example.my_app.DTO.Products.Products_SupportsDTO;
import com.example.my_app.Enum.Products.StatusBrandsProducts;

import com.example.my_app.Enum.Products.StatusCategory;
import com.example.my_app.Enum.Role_Permission.StatusPermission;
import com.example.my_app.Enum.Role_Permission.StatusRole;

import com.example.my_app.Repository.Permission.PermissionRepository;
import com.example.my_app.Repository.Products.BrandsRepository;
import com.example.my_app.Repository.Products.CategoryRepository;
import com.example.my_app.Repository.Role.RoleRepository;

import com.example.my_app.Repository.User.UserRepository;
import com.example.my_app.custom.CustomRepository.RoleCustom;
import com.example.my_app.model.Product.ProductsCategory;
import com.example.my_app.model.Product.Products_Brands;
import com.example.my_app.model.Role_Permission.Permission;
import com.example.my_app.model.Role_Permission.Role;
import com.example.my_app.model.User.User;
import com.example.my_app.modules.Products.Admin_ProductsServices;
import com.example.my_app.modules.Products.Request.RequestAdd;
import com.example.my_app.modules.ProductsDetail.ProductsServices;

import jakarta.persistence.EntityManager;

@SpringBootApplication
@EnableCaching
@EntityScan(basePackages = "com.example.my_app.model")
@EnableJpaRepositories(basePackages = "com.example.my_app.Repository")

public class MyAppApplication implements CommandLineRunner {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleCustom roleCustom;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EntityManager entityManager;

	@Autowired
	BrandsRepository brandsRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	Admin_ProductsServices productsServices;

	private List<RequestAdd> buildSampleRequestList() {
		List<RequestAdd> requestList = new ArrayList<>();
	
		// UUID cố định cho brand và category
		UUID brandId = UUID.fromString("b1c2d3e4-f5a6-4789-bcde-234567890abc");
		UUID categoryId = UUID.fromString("c1d2e3f4-a5b6-4789-cdef-34567890abcd");
	
		// Tạo danh sách URL hình ảnh quần áo sử dụng Pexels (chuyển thành ArrayList để có thể add thêm)
		List<String> imageUrls = new ArrayList<>(Arrays.asList(
			"https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg",
            "https://images.pexels.com/photos/994234/pexels-photo-994234.jpeg",
            "https://images.pexels.com/photos/1926769/pexels-photo-1926769.jpeg",
            "https://images.pexels.com/photos/157675/pexels-photo-157675.jpeg",
            "https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg",
            "https://images.pexels.com/photos/298863/pexels-photo-298863.jpeg",
            "https://images.pexels.com/photos/1126993/pexels-photo-1126993.jpeg",
            "https://images.pexels.com/photos/1598505/pexels-photo-1598505.jpeg",
            "https://images.pexels.com/photos/2292953/pexels-photo-2292953.jpeg",
            "https://images.pexels.com/photos/1036623/pexels-photo-1036623.jpeg",
            "https://images.pexels.com/photos/1857375/pexels-photo-1857375.jpeg",
            "https://images.pexels.com/photos/312529/pexels-photo-312529.jpeg",
            "https://images.pexels.com/photos/325876/pexels-photo-325876.jpeg",
            "https://images.pexels.com/photos/135620/pexels-photo-135620.jpeg",
            "https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg",
            "https://images.pexels.com/photos/206434/pexels-photo-206434.jpeg",
            "https://images.pexels.com/photos/767944/pexels-photo-767944.jpeg",
            "https://images.pexels.com/photos/4493568/pexels-photo-4493568.jpeg",
            "https://images.pexels.com/photos/3755708/pexels-photo-3755708.jpeg",
            "https://images.pexels.com/photos/428338/pexels-photo-428338.jpeg",
            "https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg",
            "https://images.pexels.com/photos/994234/pexels-photo-994234.jpeg",
            "https://images.pexels.com/photos/1926769/pexels-photo-1926769.jpeg",
            "https://images.pexels.com/photos/157675/pexels-photo-157675.jpeg",
            "https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg",
            "https://images.pexels.com/photos/298863/pexels-photo-298863.jpeg",
            "https://images.pexels.com/photos/1126993/pexels-photo-1126993.jpeg",
            "https://images.pexels.com/photos/1598505/pexels-photo-1598505.jpeg",
            "https://images.pexels.com/photos/2292953/pexels-photo-2292953.jpeg",
            "https://images.pexels.com/photos/1036623/pexels-photo-1036623.jpeg",
            "https://images.pexels.com/photos/1857375/pexels-photo-1857375.jpeg",
            "https://images.pexels.com/photos/312529/pexels-photo-312529.jpeg",
            "https://images.pexels.com/photos/325876/pexels-photo-325876.jpeg",
            "https://images.pexels.com/photos/135620/pexels-photo-135620.jpeg",
            "https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg",
            "https://images.pexels.com/photos/206434/pexels-photo-206434.jpeg",
            "https://images.pexels.com/photos/767944/pexels-photo-767944.jpeg",
            "https://images.pexels.com/photos/4493568/pexels-photo-4493568.jpeg",
            "https://images.pexels.com/photos/3755708/pexels-photo-3755708.jpeg",
            "https://images.pexels.com/photos/428338/pexels-photo-428338.jpeg",
            "https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg",
            "https://images.pexels.com/photos/994234/pexels-photo-994234.jpeg",
            "https://images.pexels.com/photos/1926769/pexels-photo-1926769.jpeg",
            "https://images.pexels.com/photos/157675/pexels-photo-157675.jpeg",
            "https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg",
            "https://images.pexels.com/photos/298863/pexels-photo-298863.jpeg",
            "https://images.pexels.com/photos/1126993/pexels-photo-1126993.jpeg",
            "https://images.pexels.com/photos/1598505/pexels-photo-1598505.jpeg",
            "https://images.pexels.com/photos/2292953/pexels-photo-2292953.jpeg",
            "https://images.pexels.com/photos/1036623/pexels-photo-1036623.jpeg",
            "https://images.pexels.com/photos/1857375/pexels-photo-1857375.jpeg",
            "https://images.pexels.com/photos/312529/pexels-photo-312529.jpeg",
            "https://images.pexels.com/photos/325876/pexels-photo-325876.jpeg",
            "https://images.pexels.com/photos/135620/pexels-photo-135620.jpeg",
            "https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg",
            "https://images.pexels.com/photos/206434/pexels-photo-206434.jpeg",
            "https://images.pexels.com/photos/767944/pexels-photo-767944.jpeg",
            "https://images.pexels.com/photos/4493568/pexels-photo-4493568.jpeg",
            "https://images.pexels.com/photos/3755708/pexels-photo-3755708.jpeg",
            "https://images.pexels.com/photos/428338/pexels-photo-428338.jpeg",
            "https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg",
            "https://images.pexels.com/photos/994234/pexels-photo-994234.jpeg",
            "https://images.pexels.com/photos/1926769/pexels-photo-1926769.jpeg",
            "https://images.pexels.com/photos/157675/pexels-photo-157675.jpeg",
            "https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg",
            "https://images.pexels.com/photos/298863/pexels-photo-298863.jpeg",
            "https://images.pexels.com/photos/1126993/pexels-photo-1126993.jpeg",
            "https://images.pexels.com/photos/1598505/pexels-photo-1598505.jpeg",
            "https://images.pexels.com/photos/2292953/pexels-photo-2292953.jpeg",
            "https://images.pexels.com/photos/1036623/pexels-photo-1036623.jpeg",
            "https://images.pexels.com/photos/1857375/pexels-photo-1857375.jpeg",
            "https://images.pexels.com/photos/312529/pexels-photo-312529.jpeg",
            "https://images.pexels.com/photos/325876/pexels-photo-325876.jpeg",
            "https://images.pexels.com/photos/135620/pexels-photo-135620.jpeg",
            "https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg",
            "https://images.pexels.com/photos/206434/pexels-photo-206434.jpeg",
            "https://images.pexels.com/photos/767944/pexels-photo-767944.jpeg",
            "https://images.pexels.com/photos/4493568/pexels-photo-4493568.jpeg",
            "https://images.pexels.com/photos/3755708/pexels-photo-3755708.jpeg",
            "https://images.pexels.com/photos/428338/pexels-photo-428338.jpeg",
            "https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg",
            "https://images.pexels.com/photos/994234/pexels-photo-994234.jpeg",
            "https://images.pexels.com/photos/1926769/pexels-photo-1926769.jpeg",
            "https://images.pexels.com/photos/157675/pexels-photo-157675.jpeg",
            "https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg",
            "https://images.pexels.com/photos/298863/pexels-photo-298863.jpeg",
            "https://images.pexels.com/photos/1126993/pexels-photo-1126993.jpeg",
            "https://images.pexels.com/photos/1598505/pexels-photo-1598505.jpeg",
            "https://images.pexels.com/photos/2292953/pexels-photo-2292953.jpeg",
            "https://images.pexels.com/photos/1036623/pexels-photo-1036623.jpeg",
            "https://images.pexels.com/photos/1857375/pexels-photo-1857375.jpeg",
            "https://images.pexels.com/photos/312529/pexels-photo-312529.jpeg",
            "https://images.pexels.com/photos/325876/pexels-photo-325876.jpeg",
            "https://images.pexels.com/photos/135620/pexels-photo-135620.jpeg",
            "https://images.pexels.com/photos/996329/pexels-photo-996329.jpeg",
            "https://images.pexels.com/photos/206434/pexels-photo-206434.jpeg",
            "https://images.pexels.com/photos/767944/pexels-photo-767944.jpeg",
            "https://images.pexels.com/photos/4493568/pexels-photo-4493568.jpeg",
            "https://images.pexels.com/photos/3755708/pexels-photo-3755708.jpeg",
            "https://images.pexels.com/photos/428338/pexels-photo-428338.jpeg"
		));
	
		// Thêm ảnh từ Unsplash để mở rộng danh sách
		int unsplashImageCount = 100;
		for (int i = 0; i < unsplashImageCount; i++) {
			imageUrls.add("https://source.unsplash.com/600x400/?clothes&sig=" + i);
		}
	
		// Lấy kích thước thực của danh sách ảnh hiện có
		int totalImages = imageUrls.size();
	
		// Tạo danh sách sample cho 100 sản phẩm sử dụng các URL ảnh có sẵn
		for (int i = 0; i < 10; i++) {
			UUID productId = UUID.randomUUID();
	
			ProductsDTO product = ProductsDTO.builder()
					.id(productId)
					.name("T-Shirt Model #" + (i + 1))
					.quantity(30 + i * 5)
					.minPrice(150000 + i * 10000)
					.maxPrice(200000 + i * 10000)
					.totalBUY(i * 3)
					.title("Exclusive Collection #" + (i + 1))
					// Chọn ảnh theo chỉ số, dùng modulo với totalImages để đảm bảo luôn trong phạm vi index hợp lệ
					.url(imageUrls.get(i % totalImages))
					.isActive(true)
					.build();
	
			ProductsBrandDTO brand = new ProductsBrandDTO(
					brandId,
					"ADIDAS",
					null);
	
			ProductsCategoryDTO category = new ProductsCategoryDTO(
					categoryId,
					"sports",
					null);
	
			// Tạo danh sách 2 ảnh cho mỗi sản phẩm
			List<ProductsImgDTO> images = List.of(
					new ProductsImgDTO(UUID.randomUUID(), imageUrls.get(i % totalImages)),
					new ProductsImgDTO(UUID.randomUUID(), imageUrls.get((i + 1) % totalImages))
			);
	
			// Tạo danh sách các thuộc tính hỗ trợ của sản phẩm (ví dụ kích cỡ S và M)
			List<ProductsSupportAttributeDTO> attributes = List.of(
					ProductsSupportAttributeDTO.builder()
							.id(UUID.randomUUID())
							.size("S")
							.sellingPrice(150000 + i * 5000)
							.costPrice(100000 + i * 3000)
							.quantity(20 + i * 2)
							.build(),
					ProductsSupportAttributeDTO.builder()
							.id(UUID.randomUUID())
							.size("M")
							.sellingPrice(160000 + i * 5000)
							.costPrice(110000 + i * 3000)
							.quantity(25 + i * 2)
							.build()
			);
	
			// Tạo đối tượng hỗ trợ cho sản phẩm với 1 ảnh được chọn từ danh sách
			Products_SupportsDTO support = Products_SupportsDTO.builder()
					.id(UUID.randomUUID())
					.url(imageUrls.get((i + 2) % totalImages))
					.color("Color " + (i + 1))
					.codecolor("CODE" + (i + 1))
					.isActive(true)
					.products_id(product)
					.products_Supports_Products_Support_Attribute(new HashSet<>(attributes))
					.build();
	
			requestList.add(new RequestAdd(product, brand, category, images, List.of(support)));
		}
	
		return requestList;
	}
	

	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		for (StatusPermission value : StatusPermission.values()) {
			Permission permission = new Permission();
			permission.setDescription(value);
			permissionRepository.save(permission);
		}

		for (StatusBrandsProducts value : StatusBrandsProducts.values()) {
			Products_Brands pBrands = new Products_Brands();
			pBrands.setBrands(value.toString());
			brandsRepository.save(pBrands);
		}
		for (StatusCategory value : StatusCategory.values()) {
			ProductsCategory pBrands = new ProductsCategory();
			pBrands.setCategory(value.toString());
			categoryRepository.save(pBrands);
		}
		List<RequestAdd> a = buildSampleRequestList();
		for (RequestAdd value : a) {
			boolean b = productsServices.addNewProducts(value);
			System.out.println(b);
		}
		User user = new User();
		user.setPassword(passwordEncoder.encode("123"));
		user.setEmail("nguyennhatquang522004@gmail.com");
		user.setUsername("nguyennhatquang");
		userRepository.save(user);
		Role check = roleCustom.handleDefaultPermissionRole(StatusRole.Customers,
				user);
		user.setUser_role(check);
		userRepository.save(user);
	}

}
