package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.dto.book.CreateOrUpdateBookDTO;
import io.github.xmchxup.latticy.model.BookDO;

import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
public interface BookService {

    boolean createBook(CreateOrUpdateBookDTO validator);

    List<BookDO> getBookByKeyword(String q);

    boolean updateBook(BookDO book, CreateOrUpdateBookDTO validator);

    BookDO getById(Integer id);

    List<BookDO> findAll();

    boolean deleteById(Integer id);
}
