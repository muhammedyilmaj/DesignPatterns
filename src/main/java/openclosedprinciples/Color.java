/**
    # software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification
 **/
package openclosedprinciples;

import java.util.List;
import java.util.stream.Stream;

public enum Color {
    RED, GREEN, BLUE
}
enum Size {
    SMALL, MEDIUM , LARGE
}
class Product {
    public String name;
    public Color color;
    public Size size;
    public Product(String name, Color color, Size size){
        this.name = name;
        this.color = color;
        this.size = size;
    }
}
class ProductFilter {
    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(s -> s.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(s -> s.size == size);
    }
    public Stream<Product> filterBySizeAndColor (List<Product> products, Size size, Color color) {
        return products.stream().filter(s -> s.size == size && s.color == color);
    }
}
class Demo {
    public static void main(String[] args) {
        Product apple = new Product("apple", Color.GREEN, Size.SMALL);
        Product tree = new Product("tree", Color.GREEN, Size.LARGE);
        Product house = new Product("house", Color.BLUE, Size.LARGE);

        List<Product> products = List.of(apple,tree, house);
        ProductFilter pf = new ProductFilter();
        pf.filterByColor(products, Color.GREEN)
                .forEach(p -> System.out.println(" - " + p.name + " is green"));

        BetterFilter bf =  new BetterFilter();
        bf.filter(products,new ColorSpecification(Color.GREEN))
                .forEach(p -> System.out.println("- " + p.name + "is green"));
        bf.filter(products,
                new AndSpecification<>(
                        new ColorSpecification(Color.BLUE),
                        new SizeSpecification(Size.LARGE)))
                .forEach(p -> System.out.println("- " + p.name + "is green and large"));
    }
}
interface Specification<T> {
    boolean isSatisfied(T item);
}
interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}
class ColorSpecification implements Specification<Product> {
    private Color color;

    ColorSpecification (Color color) {
        this.color = color;
    }
    @Override
    public boolean isSatisfied(Product item) {
        return item.color == color;
    }
}
class SizeSpecification implements Specification<Product> {
    private Size size;

    SizeSpecification (Size size) {
        this.size = size;
    }
    @Override
    public boolean isSatisfied(Product item) {
        return item.size == size;
    }
}
class BetterFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter( p -> spec.isSatisfied(p));
    }
}
class AndSpecification<T> implements Specification<T>{
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}