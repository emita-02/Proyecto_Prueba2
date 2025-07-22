/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 6.9.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenu>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpinBox>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralwidget;
    QLabel *label;
    QLabel *label_2;
    QLabel *label_3;
    QLabel *label_4;
    QPushButton *pushButtonAgregar;
    QPushButton *pushButtonActualizar;
    QPushButton *pushButtonComprar;
    QPushButton *pushButtonLista;
    QLineEdit *lineEditMarca;
    QLineEdit *lineEditModelo;
    QLineEdit *lineEditPrecio;
    QSpinBox *spinBoxAnio;
    QLabel *labelImagen;
    QLabel *labelImagen2;
    QMenuBar *menubar;
    QMenu *menuCRUD_AUTOS_ESFOT;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName("MainWindow");
        MainWindow->resize(469, 600);
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName("centralwidget");
        label = new QLabel(centralwidget);
        label->setObjectName("label");
        label->setGeometry(QRect(40, 40, 31, 21));
        label_2 = new QLabel(centralwidget);
        label_2->setObjectName("label_2");
        label_2->setGeometry(QRect(40, 80, 41, 31));
        label_3 = new QLabel(centralwidget);
        label_3->setObjectName("label_3");
        label_3->setGeometry(QRect(40, 130, 31, 16));
        label_4 = new QLabel(centralwidget);
        label_4->setObjectName("label_4");
        label_4->setGeometry(QRect(40, 160, 21, 21));
        pushButtonAgregar = new QPushButton(centralwidget);
        pushButtonAgregar->setObjectName("pushButtonAgregar");
        pushButtonAgregar->setGeometry(QRect(230, 40, 101, 41));
        pushButtonActualizar = new QPushButton(centralwidget);
        pushButtonActualizar->setObjectName("pushButtonActualizar");
        pushButtonActualizar->setGeometry(QRect(350, 40, 101, 41));
        pushButtonComprar = new QPushButton(centralwidget);
        pushButtonComprar->setObjectName("pushButtonComprar");
        pushButtonComprar->setGeometry(QRect(230, 100, 101, 41));
        pushButtonLista = new QPushButton(centralwidget);
        pushButtonLista->setObjectName("pushButtonLista");
        pushButtonLista->setGeometry(QRect(350, 100, 101, 41));
        lineEditMarca = new QLineEdit(centralwidget);
        lineEditMarca->setObjectName("lineEditMarca");
        lineEditMarca->setGeometry(QRect(90, 40, 113, 24));
        lineEditModelo = new QLineEdit(centralwidget);
        lineEditModelo->setObjectName("lineEditModelo");
        lineEditModelo->setGeometry(QRect(90, 80, 113, 24));
        lineEditPrecio = new QLineEdit(centralwidget);
        lineEditPrecio->setObjectName("lineEditPrecio");
        lineEditPrecio->setGeometry(QRect(90, 120, 113, 24));
        spinBoxAnio = new QSpinBox(centralwidget);
        spinBoxAnio->setObjectName("spinBoxAnio");
        spinBoxAnio->setGeometry(QRect(90, 160, 111, 31));
        spinBoxAnio->setMinimum(1970);
        spinBoxAnio->setMaximum(2025);
        spinBoxAnio->setValue(2025);
        labelImagen = new QLabel(centralwidget);
        labelImagen->setObjectName("labelImagen");
        labelImagen->setGeometry(QRect(10, 0, 461, 381));
        labelImagen->setScaledContents(true);
        labelImagen2 = new QLabel(centralwidget);
        labelImagen2->setObjectName("labelImagen2");
        labelImagen2->setGeometry(QRect(10, 0, 81, 31));
        labelImagen2->setScaledContents(true);
        MainWindow->setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar->setObjectName("menubar");
        menubar->setGeometry(QRect(0, 0, 469, 17));
        menuCRUD_AUTOS_ESFOT = new QMenu(menubar);
        menuCRUD_AUTOS_ESFOT->setObjectName("menuCRUD_AUTOS_ESFOT");
        MainWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar->setObjectName("statusbar");
        MainWindow->setStatusBar(statusbar);

        menubar->addAction(menuCRUD_AUTOS_ESFOT->menuAction());

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QCoreApplication::translate("MainWindow", "MainWindow", nullptr));
        label->setText(QCoreApplication::translate("MainWindow", "Marca", nullptr));
        label_2->setText(QCoreApplication::translate("MainWindow", "Modelo", nullptr));
        label_3->setText(QCoreApplication::translate("MainWindow", "Precio", nullptr));
        label_4->setText(QCoreApplication::translate("MainWindow", "A\303\261o", nullptr));
        pushButtonAgregar->setText(QCoreApplication::translate("MainWindow", "Agregar", nullptr));
        pushButtonActualizar->setText(QCoreApplication::translate("MainWindow", "Actualizar", nullptr));
        pushButtonComprar->setText(QCoreApplication::translate("MainWindow", "Eliminar", nullptr));
        pushButtonLista->setText(QCoreApplication::translate("MainWindow", "Lista", nullptr));
        labelImagen->setText(QCoreApplication::translate("MainWindow", "labelImagen", nullptr));
        labelImagen2->setText(QCoreApplication::translate("MainWindow", "TextLabel", nullptr));
        menuCRUD_AUTOS_ESFOT->setTitle(QCoreApplication::translate("MainWindow", "CRUD AUTOS-ESFOT", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
