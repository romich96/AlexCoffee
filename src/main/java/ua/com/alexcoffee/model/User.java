package ua.com.alexcoffee.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Класс описывает сущность "Пользователь", наследует класс {@link Model}
 * и реализует методы интерфейса {@link UserDetails}.
 * В классе обязательно поле role, то есть можно создавать пользователей
 * с разными правами (администраторы клиенты и т.д).
 * Аннотация @Entity говорит о том что объекты этого класса будет
 * обрабатываться hibernate.
 * Аннотация @Table(name = "users") указывает на таблицу "users",
 * в которой будут храниться объекты.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.2
 * @see Role
 * @see Order
 */
@Entity
@Table(name = "users")
public final class User extends Model implements UserDetails {
    /**
     * Номер версии класса необходимый для десериализации и сериализации.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Имя пользователя.
     * Значение поля сохраняется в колонке "name".
     * Не может быть null.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Имя пользователя для входа в учетную запись на сайте (логин).
     * Значение поля сохраняется в колонке "username".
     */
    @Column(name = "username")
    private String username;

    /**
     * Пароль пользователя для входа в учетную запись на сайте.
     * Значение поля сохраняется в колонке "password".
     */
    @Column(name = "password")
    private String password;

    /**
     * Электронная почта пользователя.
     * Значение поля сохраняется в колонке "email".
     * Не может быть null.
     */
    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    /**
     * Номер телефона пользователя.
     * Значение поля сохраняется в колонке "phone".
     * Не может быть null.
     */
    @Column(
            name = "phone",
            nullable = false
    )
    private String phone;

    /**
     * Ссылка на страничку в соц. сети "ВКонтакте" пользователя.
     * Значение поля сохраняется в колонке "vkontakte".
     */
    @Column(name = "vkontakte")
    private String vkontakte;

    /**
     * Ссылка на страничку в соц. сети "Facebook" пользователя.
     * Значение поля сохраняется в колонке "facebook".
     */
    @Column(name = "facebook")
    private String facebook;

    /**
     * Логин пользователя в месенджере "Skype".
     * Значение поля сохраняется в колонке "skype".
     */
    @Column(name = "skype")
    private String skype;

    /**
     * Описание заказа.
     * Значение поля сохраняется в колонке "description".
     */
    @Column(name = "description")
    private String description;

    /**
     * Роль пользователя.
     * Значение поля (id объекта role) сохраняется в колонке "role_id".
     * Не может быть null.
     * Между объектами классов {@link User} и {@link Role} связь многие-к-одному,
     * а именно много разных пользователей могут иметь одинаковую роль (права) на сайте.
     * Выборка объекта status до первого доступа нему, при первом доступе к текущему объекту.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
    )
    private Role role;

    /**
     * Список заказов, которые сделал текущий клиент.
     * К текущему пользователю можно добраться через поле "client"
     * в объекте класса {@link Order}.
     * Выборка продаж при первом доступе к текущему объекту.
     * Сущности clientOrders автоматически удаляются при удалении текущей сущности.
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "client",
            cascade = CascadeType.REMOVE
    )
    private List<Order> clientOrders = new ArrayList<>();

    /**
     * Список заказов, которые обработал текущий менеджер.
     * К текущему пользователю можно добраться через поле "manager"
     * в объекте класса {@link Order}.
     * Выборка продаж при первом доступе к текущему объекту.
     * Сущности managerOrders автоматически удаляются
     * при удалении текущей сущности.
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "manager",
            cascade = CascadeType.REMOVE
    )
    private List<Order> managerOrders = new ArrayList<>();

    /**
     * Конструктр без параметров.
     */
    public User() {
        this("", "", "", null);
    }

    /**
     * Конструктор для инициализации основных переменных заказа.
     *
     * @param name  Имя пользователя.
     * @param email Электронная почта пользователя.
     * @param phone Номер телефона пользователя.
     * @param role  Роль пользователя.
     */
    public User(
            final String name,
            final String email,
            final String phone,
            final Role role
    ) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.username = "";
        this.password = "";
        this.vkontakte = "";
        this.facebook = "";
        this.skype = "";
        this.description = "";
    }

    /**
     * Возвращает описание пользователя.
     * Переопределенный метод родительского класса {@link Object}.
     *
     * @return Значение типа {@link String} - строка описание пользователя
     * (имя, роль, электронная почта, номер телефона).
     */
    @Override
    public String toString() {
        return "Name: " + this.name
                + "\nRole: " + this.role.getDescription()
                + "\nEmail: " + this.email
                + "\nPhone: " + this.phone;
    }

    /**
     * Генерирует строку для конечного сравнения пользователей
     * в методе equals() родительского класса.
     * Переопределенный метод родительского класса {@link Model}.
     *
     * @return Значение типа {@link String} -
     * имя пользователя + электронная почта + номер телефона.
     */
    @Override
    public String toEquals() {
        return getName() + getEmail() + getPhone();
    }

    /**
     * Возвращает значение типа boolean в зависемости от срока действия аккаунта.
     * Реализованый метод интерфейса {@link UserDetails}.
     *
     * @return {@code true} - если текущий аккаунт работоспособный.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    /**
     * Возвращает значение типа boolean от того,
     * заблокирован текущий аккаунт (пользователь) или нет.
     * Реализованый метод интерфейса {@link UserDetails}.
     *
     * @return {@code true} - если текущий аккаунт не заблокирован.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Возвращает значение типа boolean от того, активны ли права (полномичия)
     * данного аккаунта или нет.
     * Реализованый метод интерфейса {@link UserDetails}.
     *
     * @return {@code true} - если срок прав текущего аккаунта не истек.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Возвращает значение типа boolean от того,
     * активный ли текущий аккаунт или нет.
     * Реализованый метод интерфейса {@link UserDetails}.
     *
     * @return {@code true} - если текущий аккаунт активный.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Возвращает список всех ролей пользователя через объект-обертку
     * класса SimpleGrantedAuthority.
     * Реализованый метод интерфейса {@link UserDetails}.
     *
     * @return Объект типа {@link List} -
     * список ролей пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(
                new SimpleGrantedAuthority(
                        "ROLE_" + this.role.getTitle().name()
                )
        );
        return roles;
    }

    /**
     * Инициализация полей пользователя.
     *
     * @param name        Имя пользвателя.
     * @param username    Логин пользователя.
     * @param password    Пароль пользователя.
     * @param email       Электронная почта пользователя.
     * @param phone       Номер телефона пользователя.
     * @param vkontakte   Ссылка на страничку в соц. сети "ВКонтакте" пользователя.
     * @param facebook    Ссылка на страничку в соц. сети "Facebook" пользователя.
     * @param skype       Логин пользователя в месенджере "Skype".
     * @param description Описание пользователя.
     * @param role        Роль пользователя.
     */
    public void initialize(
            final String name,
            final String username,
            final String password,
            final String email,
            final String phone,
            final String vkontakte,
            final String facebook,
            final String skype,
            final String description,
            final Role role
    ) {
        setName(name);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setPhone(phone);
        setVkontakte(vkontakte);
        setFacebook(facebook);
        setSkype(skype);
        setDescription(description);
        setRole(role);
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return Значение типа {@link String} - имя пользователя.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param name Имя пользователя.
     */
    public void setName(final String name) {
        this.name = isNotBlank(name) ? name : "";
    }

    /**
     * Возвращает логин пользователя.
     *
     * @return Значение типа {@link String} - логин пользователя.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Устанавливает логин пользователя.
     *
     * @param username Логин пользователя.
     */
    public void setUsername(final String username) {
        this.username = isNotBlank(username) ? username : "";
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return Значение типа {@link String} - пароль пользователя.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password Пароль пользователя.
     */
    public void setPassword(final String password) {
        this.password = isNotBlank(password) ? password : "";
    }

    /**
     * Возвращает электронную почту пользователя.
     *
     * @return Значение типа {@link String} - электронная почта пользователя.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Устанавливает электронную почту пользователя.
     *
     * @param email Электронная почта пользователя.
     */
    public void setEmail(final String email) {
        this.email = isNotBlank(email) ? email : "";
    }

    /**
     * Метод возвращает номер телефона пользвателя.
     *
     * @return Значение типа {@link String} - номер телефона пользвателя.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Устанавливает номер телефона пользователя.
     *
     * @param phone Номер телефона пользователя.
     */
    public void setPhone(final String phone) {
        this.phone = isNotBlank(phone) ? phone : "";
    }

    /**
     * Метод возвращает ссылку на страничку в соц. сети "ВКонтакте" пользователя.
     *
     * @return Значение типа {@link String} - ссылка "ВКонтакте" пользователя.
     */
    public String getVkontakte() {
        return this.vkontakte;
    }

    /**
     * Устанавливает ссылку на страничку в соц. сети "ВКонтакте" пользователя.
     *
     * @param vkontakte Ссылка "ВКонтакте" пользователя.
     */
    public void setVkontakte(final String vkontakte) {
        this.vkontakte = isNotBlank(vkontakte) ? vkontakte : "";
    }

    /**
     * Метод возвращает ссылку на страничку в соц. сети "Facebook" пользователя.
     *
     * @return Значение типа {@link String} - ссылка "Facebook" пользователя.
     */
    public String getFacebook() {
        return this.facebook;
    }

    /**
     * Устанавливает ссылку на страничку в соц. сети "Facebook" пользователя.
     *
     * @param facebook Ссылка "Facebook" пользователя.
     */
    public void setFacebook(final String facebook) {
        this.facebook = isNotBlank(facebook) ? facebook : "";
    }

    /**
     * Метод возвращает логин пользователя в месенджере "Skype".
     *
     * @return Значение типа {@link String} - логин "Skype".
     */
    public String getSkype() {
        return this.skype;
    }

    /**
     * Устанавливает логин пользователя в месенджере "Skype".
     *
     * @param skype Логин "Skype".
     */
    public void setSkype(final String skype) {
        this.skype = isNotBlank(skype) ? skype : "";
    }

    /**
     * Возвращает описание пользователя.
     *
     * @return Значение типа {@link String} - описание пользователя.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Устанавливает описание пользователя.
     *
     * @param description Описание пользователя.
     */
    public void setDescription(final String description) {
        this.description = isNotBlank(description) ? description : "";
    }

    /**
     * Возвращает роль пользователя.
     *
     * @return Объект класса {@link Role} - роль пользователя.
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Устанавливает роль пользователя.
     *
     * @param role Роль пользователя.
     */
    public void setRole(final Role role) {
        this.role = role;
    }

    /**
     * Конвертирует список заказов, которые оформил
     * текущий клиент, в список только для чтений и возвращает его.
     *
     * @return Объект типа {@link List} - список заказов только для чтения
     * или пустой список.
     */
    public List<Order> getClientOrders() {
        return getUnmodifiableList(this.clientOrders);
    }

    /**
     * Устанавливает список заказов, которые оформил текущий клиент.
     *
     * @param clientOrders Список заказов, оформленных клиентом.
     */
    public void setClientOrders(final List<Order> clientOrders) {
        this.clientOrders = clientOrders;
    }

    /**
     * Конвертирует список заказов, которые обработал текущий менеджер,
     * в список только для чтений и возвращает его.
     *
     * @return Объект типа {@link List} - список заказов только
     * для чтения или пустой список.
     */
    public List<Order> getManagerOrders() {
        return getUnmodifiableList(this.managerOrders);
    }

    /**
     * Устанавливает список заказов, которые обработал текущий менеджер.
     *
     * @param managerOrders Список заказов, обработаных менеджером.
     */
    public void setManagerOrders(final List<Order> managerOrders) {
        this.managerOrders = managerOrders;
    }
}
