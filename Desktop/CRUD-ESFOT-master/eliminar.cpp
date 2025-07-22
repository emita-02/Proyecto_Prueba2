void MainWindow::on_btnEliminar_clicked() {
    bool ok;
    int idEliminar = ui->lineEditIdEliminar->text().toInt(&ok);

    if (!ok) {
        QMessageBox::warning(this, "Error", "Ingrese un ID válido.");
        return;
    }

    auto it = std::find_if(listaVehiculos.begin(), listaVehiculos.end(),
                           [idEliminar](const Vehiculo &v) { return v.id == idEliminar; });

    if (it != listaVehiculos.end()) {
        listaVehiculos.erase(it);
        QMessageBox::information(this, "Éxito", 
                                 QString("Vehículo con ID %1 eliminado.").arg(idEliminar));
    } else {
        QMessageBox::warning(this, "No encontrado", 
                             QString("No se encontró un vehículo con ID %1.").arg(idEliminar));
    }
}
