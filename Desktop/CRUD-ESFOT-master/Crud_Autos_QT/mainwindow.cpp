#include "mainwindow.h"
#include "./ui_mainwindow.h"
#include <QMessageBox>
#include <QPixmap>
#include "../vehiculos.h"


MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    cargar_imagen();

}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::cargar_imagen()
{
    // Imagen del carro
    QPixmap imagenCarro("Picture/Car_F_02-1.jpg");
    if (!imagenCarro.isNull()) {
        ui->labelImagen->setPixmap(imagenCarro);
    } else {
        qDebug() << "No se pudo cargar la imagen del carro.";
    }

    // Imagen del logo
    QPixmap logo("Picture/logo_esfot_buho.png");
    if (!logo.isNull()) {
        ui->labelImagen2->setPixmap(logo);
    } else {
        qDebug() << "No se pudo cargar el logo.";
    }
}



void MainWindow::on_pushButtonAgregar_clicked()
{
    QString marcaQt = ui->lineEditMarca->text();
    QString modeloQt = ui->lineEditModelo->text();
    int año = ui->spinBoxAnio->value();
    QString precioQt = ui->lineEditPrecio->text();

    if (marcaQt.isEmpty() || modeloQt.isEmpty() || precioQt.isEmpty()) {
        QMessageBox::warning(this, "Campos vacíos", "Por favor, llena todos los campos");
        return;
    }

    string marca = marcaQt.toStdString();
    string modelo = modeloQt.toStdString();
    double precio = precioQt.toDouble();

    bool repetido = agregarVehiculo(marca, modelo, año, precio);

    ui->lineEditMarca->clear();
    ui->lineEditModelo->clear();
    ui->spinBoxAnio->setValue(2000);
    ui->lineEditPrecio->clear();

    QMessageBox::information(this, "Vehículo guardado", repetido ?
            "Cantidad aumentada en 1." : "Vehículo nuevo agregado con cantidad 1.");
}



void MainWindow::on_labelImagen_linkActivated(const QString &link)
{

}

