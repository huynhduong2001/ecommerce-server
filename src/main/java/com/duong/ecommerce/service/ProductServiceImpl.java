package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.ProductException;
import com.duong.ecommerce.model.Category;
import com.duong.ecommerce.model.Product;
import com.duong.ecommerce.repository.CategoryRepository;
import com.duong.ecommerce.repository.ProductRepository;
import com.duong.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    private UserService userService;

    private CategoryRepository categoryRepository;

    ProductServiceImpl(ProductRepository productRepository, UserService userService,CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {
        System.out.println("request: "+req.getTopLavelCategory());

        Category topLevel = categoryRepository.findByName(req.getTopLavelCategory());

        if(topLevel == null){
            Category topLavelCategory = new Category();
            topLavelCategory.setName(req.getTopLavelCategory());
            topLavelCategory.setLever(1);

            topLevel = categoryRepository.save(topLavelCategory);

        }
        System.out.println("check lần 2 " + topLevel.getName());

        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLavelCategory(), topLevel.getName());

        if(secondLevel == null){

            Category secondLavelCategory = new Category();
            secondLavelCategory.setName(req.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLevel);
            secondLavelCategory.setLever(2);

            secondLevel = categoryRepository.save(secondLavelCategory);

        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLavelCategory(), secondLevel.getName());
        if(thirdLevel == null){
            Category thirdLavelCategory = new Category();
            thirdLavelCategory.setName(req.getThirdLavelCategory());
            thirdLavelCategory.setParentCategory(secondLevel);
            thirdLavelCategory.setLever(3);

            thirdLevel = categoryRepository.save(thirdLavelCategory);

        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPersent(req.getDiscountedPersent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreateAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted succsessfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if (req.getQuantity()!= 0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt = productRepository.findById(id);

        if(opt.isPresent()){
            return  opt.get();
        }
        throw new ProductException("Product not found with id "+ id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> opt = productRepository.findAll();
        return opt;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProduct(category,minPrice, maxPrice,minDiscount,sort);

        if (!colors.isEmpty()){
            products = products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if(stock!= null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p-> p.getQuantity()>0).collect(Collectors.toList());
            }
            else if (stock.equals("out_of_stock")){
                products = products.stream().filter(p-> p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);

        Page<Product> filteredProduct = new PageImpl<>(pageContent,pageable, products.size());
        return filteredProduct;
    }
}
