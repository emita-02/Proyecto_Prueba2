#ifndef VEHICULOS_H
#define VEHICULOS_H

#include <string>
#include <vector>
using namespace std;

struct Vehiculo {
    int id;
    string marca;
    string modelo;
    int año;
    double precio;
    int cantidad;
};

extern vector<Vehiculo> listaVehiculos;
extern int contadorID;

bool agregarVehiculo(const string& marca, const string& modelo, int año, double precio);

#endif // VEHICULOS_H
