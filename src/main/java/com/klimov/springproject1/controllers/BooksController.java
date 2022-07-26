package com.klimov.springproject1.controllers;

import com.klimov.springproject1.dao.BookDao;
import com.klimov.springproject1.dao.ClientDao;
import com.klimov.springproject1.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;
    private final ClientDao clientDao;

    @Autowired
    public BooksController(BookDao bookDao, ClientDao clientDao) {
        this.bookDao = bookDao;
        this.clientDao = clientDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDao.getBooks());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") int id,
                          Model model)
    {
        Optional<Book> book = bookDao.getBookById(id);
        if(!book.isPresent()) return "redirect:/books";
        model.addAttribute("book", book.get());
        if(book.get().getClient_id()==null) model.addAttribute("clients", clientDao.getClients());
        else model.addAttribute("client", clientDao.getClientById(book.get().getClient_id()).get());
        return "books/details";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/add";
    }

    @PostMapping()
    public String add(@ModelAttribute("book") @Valid Book book,
                      BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "books/add";
        bookDao.saveBook(book);
        return ("redirect:/books");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id)
    {
        Optional<Book> book = bookDao.getBookById(id);
        if(!book.isPresent()) return "redirect:/books";
        model.addAttribute("book", book.get());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book") @Valid Book updatedBook,
                         BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) return "books/edit";
        bookDao.updateBook(id, updatedBook);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/books";
    }
}
