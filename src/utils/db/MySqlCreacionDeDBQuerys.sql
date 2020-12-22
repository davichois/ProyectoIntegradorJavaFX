# Creamos la base de datos
create database distribuidora;
# Penemos usar la base de datos
use distribuidora;

# Creamos la tabla de usuario aparte ya que no se relacionara con ninguno por ahora......
create table usuario(
	idUsuario int not null auto_increment,
    nombre varchar(50) not null,
    contraseña varchar(60) not null,
    primary key(`idUsuario`)
);

select * from usuario;
select nombre from usuario where nombre = "admin";
insert into usuario(nombre, contraseña) 
values("admin", "admin");

select * from usuario;

# Creamos la tabla de cliente
create table cliente(
	idCliente int not null auto_increment,
    nombre varchar(50) not null,
    apellido varchar(50) null,
	dni varchar(90) not null unique,
    telefono int (12) not null,
	idZona int null,
    sexo varchar(15) not null,
    fecha_de_creacion date,
    primary key (`idCliente`),
    foreign key(idZona) references zona(idZona)
);

# Creamos la tabla de Zona
create table zona(
	idZona int not null auto_increment,
    departamento varchar(150) not null,
    provincia varchar(150) not null,
    distrito varchar(150) not null,
    primary key (`idZona`)
);

insert into zona(departamento, provincia, distrito) 
values("San Martin", "Tarapoto", "Morales");

select * from zona;

# Creamos la tabla de productos
create table producto(
	idProducto int not null auto_increment,
    nombre varchar(60) not null,
    precio int not null default 0,
    stock int not null default 0,
    descripcion varchar(250) null default "producto de distribuidora",
    codigoBarra varchar(80) not null,
    idCategoria int null default 1,
    primary key (`idProducto`),
	foreign key(idCategoria) references categoria(idCategoria)
);

insert into producto(nombre, precio, stock, descripcion, codigoBarra, idCategoria) 
values( "Leche de ganado" ,5.50 ,20,"Este es un producto alto en grasas", "87219374323", 1);

# Creamos la tabla de categorias
create table categoria(
	idCategoria int not null auto_increment,
    nombre varchar(90) not null,
    primary key (`idCategoria`)
);

insert into categoria(nombre) 
values("chocolates");

select * from categoria;

# Creamos la tabla de vendedor
create table vendedor(
	idVendedor int not null auto_increment,
    nombre varchar(80) not null,
    edad int null default 20,
    dni int not null,
    telefono int null,
    primary key(`idVendedor`)
);

# Creamos la tabla de categorias
create table venta(
	idVenta int not null auto_increment,
    tipoVenta varchar(50) not null,
    totalVenta double not null,
    idCliente int not null,
    idVendedor int not null,
    primary key (`idVenta`),
    foreign key(idCliente) references cliente(idCliente),
    foreign key(idVendedor) references vendedor(idVendedor)
);

# Tabla que une ventas y productos
create table provent(
	idProVent int not null auto_increment,
    idProducto int not null,
    idVenta int not null,
    primary key(`idProVent`),
    foreign key(idProducto) references producto(idProducto),
    foreign key(idVenta) references venta(idVenta)
);

#Querys
select * from cliente;
select * from zona;
select * from producto;
select * from categoria;
select * from vendedor;
select * from venta;
select * from provent;

insert into cliente ( nombre, apellido, dni, telefono, sexo, idZona, fecha_de_creacion ) values (
	"David", "prada linarez", "74228776", 934244709, "masculino", 1, curdate()
) ;