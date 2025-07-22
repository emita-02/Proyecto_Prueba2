#include "vehiculos.h"
#include <iostream>
using namespace std;

void mostrar() {
    cout << "=== INVENTARIO DE VEHÍCULOS ===" << endl;

    if (listaVehiculos.empty()) {
        cout << "No hay vehículos en el inventario." << endl;
        return;
    }

    cout << "ID\tMarca\tModelo\tAño\tPrecio\tCantidad" << endl;

    for (const auto& v : listaVehiculos) {
        cout << v.id << "\t" << v.marca << "\t" << v.modelo 
             << "\t" << v.año << "\t$" << v.precio 
             << "\t" << v.cantidad << endl;
    }
}
