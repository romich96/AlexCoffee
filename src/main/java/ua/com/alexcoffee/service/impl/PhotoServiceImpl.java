package ua.com.alexcoffee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.com.alexcoffee.dao.interfaces.PhotoDAO;
import ua.com.alexcoffee.exception.BadRequestException;
import ua.com.alexcoffee.exception.WrongInformationException;
import ua.com.alexcoffee.model.Photo;
import ua.com.alexcoffee.service.interfaces.PhotoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Класс сервисного слоя реализует методы доступа объектов класса {@link Photo}
 * в базе данных интерфейса {@link PhotoService}, наследует родительский
 * класс {@link MainServiceImpl}, в котором реализованы основные методы.
 * Класс помечан аннотацией @Service - аннотация обьявляющая, что этот класс представляет
 * собой сервис – компонент сервис-слоя. Сервис является подтипом класса @Component.
 * Использование данной аннотации позволит искать бины-сервисы автоматически.
 * Методы класса помечены аннотацией @Transactional - перед исполнением метода помеченного
 * данной аннотацией начинается транзакция, после выполнения метода транзакция коммитится,
 * при выбрасывании RuntimeException откатывается.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.2
 * @see MainServiceImpl
 * @see PhotoService
 * @see Photo
 * @see PhotoDAO
 */
@Service
@ComponentScan(basePackages = "ua.com.alexcoffee.dao")
public final class PhotoServiceImpl
        extends MainServiceImpl<Photo>
        implements PhotoService {

    /**
     * Реализация интерфейса {@link PhotoDAO}
     * для работы изображений с базой данных.
     */
    private final PhotoDAO dao;

    /**
     * Конструктор для инициализации основных переменных сервиса.
     * Помечаный аннотацией @Autowired, которая позволит Spring
     * автоматически инициализировать объект.
     *
     * @param dao Реализация интерфейса {@link PhotoDAO}
     *            для работы изображений с базой данных.
     */
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public PhotoServiceImpl(final PhotoDAO dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Возвращает объект-изображение из базы даных, у которого совпадает
     * уникальное название с значением входящего параметра.
     * Режим только для чтения.
     *
     * @param title Название объекта-изображения для возврата.
     * @return Объект класса {@link Photo} - объекта-изображение.
     * @throws WrongInformationException Бросает исключение,
     *                                   если пустой входной параметр title.
     * @throws BadRequestException       Бросает исключение,
     *                                   если не найден объект-изображеие с входящим параметром title.
     */
    @Override
    @Transactional(readOnly = true)
    public Photo get(final String title) throws WrongInformationException, BadRequestException {
        if (isBlank(title)) {
            throw new WrongInformationException("No photo title!");
        }
        final Photo photo = this.dao.get(title);
        if (photo == null) {
            throw new BadRequestException("Can't find photo by title " + title + "!");
        }
        return photo;
    }

    /**
     * Удаляет объект-изображение из базы даных, у которого совпадает
     * уникальное название с значением входящего параметра.
     *
     * @param title Название объекта-изображения для удаления.
     * @throws WrongInformationException Бросает исключение,
     *                                   если пустой входной параметр title.
     */
    @Override
    @Transactional
    public void remove(final String title) throws WrongInformationException {
        if (isBlank(title)) {
            throw new WrongInformationException("No photo title!");
        }
        dao.remove(title);
    }

    /**
     * Сохраняет файл в файловой системе.
     *
     * @param photo Файл для сохранения.
     */
    @Override
    @Transactional
    public void saveFile(final MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            final String filePath = Photo.PATH + photo.getOriginalFilename();
            try (OutputStream stream = new FileOutputStream(filePath)) {
                stream.write(photo.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Удаляет файл по url.
     *
     * @param url URL файла для удаления.
     */
    @Override
    @Transactional
    public void deleteFile(final String url) {
        if (isBlank(url)) {
            final File file = new File(Photo.PATH + url);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }
}
