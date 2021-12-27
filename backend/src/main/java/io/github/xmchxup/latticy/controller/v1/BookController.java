package io.github.xmchxup.latticy.controller.v1;

import io.github.xmchxup.latticy.dto.book.CreateOrUpdateBookDTO;
import io.github.xmchxup.latticy.vo.CreatedVO;
import io.github.xmchxup.latticy.vo.DeletedVO;
import io.github.xmchxup.latticy.vo.UpdatedVO;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.xmchxup.latticy.model.BookDO;
import io.github.xmchxup.latticy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@RestController
@RequestMapping("/v1/book")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public BookDO getBook(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        BookDO book = bookService.getById(id);
        if (book == null) {
            throw new NotFoundException(10022);
        }
        return book;
    }

    @GetMapping("")
    public List<BookDO> getBooks() {
        List<BookDO> books = bookService.findAll();
        return books;
    }


    @GetMapping("/search")
    public List<BookDO> searchBook(@RequestParam(value = "q", required = false, defaultValue = "") String q) {
        List<BookDO> books = bookService.getBookByKeyword("%" + q + "%");
        return books;
    }


    @PostMapping("")
    public CreatedVO createBook(@RequestBody @Validated CreateOrUpdateBookDTO validator) {
        bookService.createBook(validator);
        return new CreatedVO(12);
    }


    @PutMapping("/{id}")
    public UpdatedVO updateBook(@PathVariable("id") @Positive(message = "{id.positive}") Integer id, @RequestBody @Validated CreateOrUpdateBookDTO validator) {
        BookDO book = bookService.getById(id);
        if (book == null) {
            throw new NotFoundException(10022);
        }
        bookService.updateBook(book, validator);
        return new UpdatedVO(13);
    }


    @DeleteMapping("/{id}")
    @GroupRequired
    @PermissionMeta(value = "删除图书", module = "图书")
    public DeletedVO deleteBook(@PathVariable("id") @Positive(message = "{id.positive}") Integer id) {
        BookDO book = bookService.getById(id);
        if (book == null) {
            throw new NotFoundException(10022);
        }
        bookService.deleteById(book.getId());
        return new DeletedVO(14);
    }


}
