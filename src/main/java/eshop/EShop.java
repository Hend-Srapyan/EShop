package eshop;

import eshop.model.Category;
import eshop.model.Product;
import eshop.service.CategoryService;
import eshop.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class EShop implements Commands {

    private final static Scanner scanner = new Scanner(System.in);
    private final static CategoryService categoryService = new CategoryService();
    private final static ProductService productService = new ProductService();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_CATEGORY:
                    addCategory();
                    break;
                case EDIT_CATEGORY_BY_ID:
                    editCategoryById();
                    break;
                case DELETE_CATEGORY_BY_ID:
                    deleteCategoryById();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case EDIT_PRODUCT_BY_ID:
                    editProductById();
                    break;
                case DELETE_PRODUCT_BY_ID:
                    deleteProductById();
                    break;
                case PRINT_SUM_OF_PRODUCTS:
                    printSumOfProducts();
                    break;
                case PRINT_MAX_OF_PRICE_PRODUCT:
                    printMaxOfProduct();
                    break;
                case PRINT_MIN_OF_PRICE_PRODUCT:
                    printMinOfProduct();
                    break;
                case PRINT_AVG_OF_PRICE_PRODUCT:
                    printAvgOfProduct();
                    break;
                case PRINT_ALL_PRODUCTS:
                    System.out.println(productService.getAllProducts());
                    break;
                case PRINT_ALL_CATEGORIES:
                    System.out.println(categoryService.getAllCategories());
                    break;
                default:
                    System.err.println("INVALID COMMAND!!!");
            }
        }

    }

    private static void printSumOfProducts() {
        System.out.println(productService.getSumOfProducts());
    }

    private static void printAvgOfProduct() {
        System.out.println(productService.getProductAvgPrice());
    }

    private static void printMinOfProduct() {
        System.out.println(productService.getProductMinPrice());
    }

    private static void printMaxOfProduct() {
        System.out.println(productService.getProductMaxPrice());
    }

    private static void deleteProductById() {
        System.out.println(productService.getAllProducts());
        System.err.println("PLEASE INPUT PRODUCT ID");
        int productId = Integer.parseInt(scanner.nextLine());
        Product productById = productService.getProductById(productId);
        if (productById != null){
            productService.deleteProduct(productId);
        }else {
            System.err.println("PRODUCT WITH " + productId + " ID DOESN'T EXIST");
        }
    }

    private static void editProductById() {
        System.out.println(productService.getAllProducts());
        System.out.println("Please input product id");
        String productId = scanner.nextLine();
        if (productId != null){
            Product productById = productService.getProductById(Integer.parseInt(productId));
            System.out.println("Please input product name");
            String productName = scanner.nextLine();
            productById.setName(productName);
            System.out.println("Please input product description");
            String productDc = scanner.nextLine();
            productById.setDescription(productDc);
            System.out.println("Please input product price");
            double productPrice = Double.parseDouble(scanner.nextLine());
            productById.setPrice(productPrice);
            System.out.println("Please input product qty");
            int productQty = Integer.parseInt(scanner.nextLine());
            productById.setQty(productQty);
            productService.editProduct(productById);
        }else {
            System.err.println("WRONG ID");
        }
    }

    private static void deleteCategoryById() {
        System.out.println(categoryService.getAllCategories());
        System.err.println("PLEASE INPUT CATEGORY ID");
        int categoryId = Integer.parseInt(scanner.nextLine());
        Category categoryById = categoryService.getCategoryById(categoryId);
        if (categoryById != null){
            categoryService.deleteCategory(categoryId);
        }else {
            System.err.println("CATEGORY WITH " + categoryId + " ID DOESN'T EXIST");
        }
    }

    private static void editCategoryById() {
        System.out.println(categoryService.getAllCategories());
        System.err.println("PLEASE INPUT CATEGORY ID");
        String categoryId = scanner.nextLine();
        if (categoryId != null){
            Category categoryById = categoryService.getCategoryById(Integer.parseInt(categoryId));
            System.out.println("Please input category name");
            String categoryName = scanner.nextLine();
            categoryById.setName(categoryName);
            categoryService.editCategory(categoryById);
        }else {
            System.err.println("WRONG ID");
        }
    }

    private static void addProduct() {
        List<Category> allCategories = categoryService.getAllCategories();
        System.err.println("HERE ARE ALL CATEGORIES:");
        for (Category category : allCategories) {
            System.out.println(category.getId() + " --> " + category.getName());
        }
        System.out.println("Please input category id");
        int categoryId = Integer.parseInt(scanner.nextLine());
        Category categoryById = categoryService.getCategoryById(categoryId);
        if (categoryById != null) {
            System.out.println("Please input product name, description, price, qty");
            String productDataStr = scanner.nextLine();
            String[] productDataArr = productDataStr.split(",");
            productService.add(Product.builder()
                    .name(productDataArr[0])
                    .description(productDataArr[1])
                    .price(Double.parseDouble(productDataArr[2]))
                    .qty(Integer.parseInt(productDataArr[3]))
                    .category(categoryById)
                    .build());
            System.err.println("PRODUCT ADDED!!!");
        }
    }

    private static void addCategory() {
        System.out.println("Please input category name");
        String categoryDataStr = scanner.nextLine();
        String[] categoryDataArr = categoryDataStr.split(",");
        categoryService.add(Category.builder()
                .name(categoryDataArr[0])
                .build());
        System.err.println("CATEGORY ADDED!!!");
    }
}
