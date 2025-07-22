#include "vehiculos.h"
#include <fstream> // Archivos

void guardarEnArchivo(){
	ofstream archivo("Datos/autos.txt");
	if(!archivo.is_open());
	
	for (const auto& v : listaVehiculos) {
        archivo << v.id << "," << v.marca << "," << v.modelo << ","
                << v.año << "," << v.precio << "," << v.cantidad << "\n";
    }
    archivo.close();
}


bool agregarVehiculo(const string& marca,const string& modelo,int año,double precio){
	for(auto& v: listaVehiculos){
		if(v.marca== marca && v.modelo ==modelo){
			v.cantidad +=1;
			guardarEnArchivo();
			return true;
		}
	}
	Vehiculo nuevo;
	nuevo.id = contadorID++;
	nuevo.marca = marca;
	nuevo.modelo = modelo;
	nuevo.año = año;
	nuevo.precio = precio;
	nuevo.cantidad = 1;

	listaVehiculos.push_back(nuevo);
	guardarEnArchivo();
	return false;
}


