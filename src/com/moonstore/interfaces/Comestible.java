package com.moonstore.interfaces;

import java.time.LocalDate;

public interface Comestible {
    LocalDate getFechaVencimiento();
    int getCalorias();
    void setFechaVencimiento(LocalDate fechaVencimiento);
    void setCalorias(int calorias);
}