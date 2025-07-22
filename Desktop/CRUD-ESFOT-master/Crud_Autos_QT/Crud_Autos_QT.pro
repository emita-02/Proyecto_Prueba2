QT += widgets

CONFIG += c++17
CONFIG += console
CONFIG -= app_bundle

TEMPLATE = app

SOURCES += \
    main.cpp \
    mainwindow.cpp \
    ../agregar.cpp \
    ../eliminar.cpp \
    ../actualizar.cpp \
    ../Mostrar.cpp \
    ../vehiculos.cpp

HEADERS += \
    mainwindow.h \
    ../vehiculos.h

FORMS += \
    mainwindow.ui
