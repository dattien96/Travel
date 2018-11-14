package com.example.dattienbkhn.travel.screen.common;

import com.example.dattienbkhn.travel.network.message.map.direction.Leg;

/**
 * Created by dattienbkhn on 04/03/2018.
 */

public interface IMapDetailsEvent {

    void onMapClick();

    void onPolylineClick(int roadPos);


}
