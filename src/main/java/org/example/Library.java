package org.example;

import java.util.*;

public class Library {
    public List<Book> books = new ArrayList<>();
    public Set<Member> members = new HashSet<>();
    public Map<Member, List<Book>> borrowedBooks = new HashMap<>();

    public void addBook(int id, String title, String author) {

        for (Book b : books) {
            if (b.id == id) {
                throw new RuntimeException("Bu ID-də kitab artıq mövcuddur!");
            }
        }
        books.add(new Book(id, title, author));
        System.out.println("Kitab uğurla əlavə olundu.");
    }

    public void addMember(int id, String name) {
        Member newMember = new Member(id, name);

        if (members.contains(newMember)) {
            throw new RuntimeException("Bu üzv artıq mövcuddur!");
        }
        members.add(newMember);

        borrowedBooks.put(newMember, new ArrayList<>());
        System.out.println("Üzv uğurla əlavə olundu.");

    }
    public Member findMember(int id) {
        for (Member m : members) {
            if (m.id == id) return m;
        }
        throw new RuntimeException("Belə bir üzv tapılmadı!");
    }

    public Book findBook(int id) {
        for (Book b : books) {
            if (b.id == id) return b;
        }
        throw new RuntimeException("Belə bir kitab tapılmadı!");
    }
}