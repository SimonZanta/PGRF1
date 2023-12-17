package model3D;

import java.util.List;

public class Scene {
    private List<Object3D> object3DList;
    public Scene(List<Object3D> object3DList){
        this.object3DList = object3DList;
    }

    public void addObjects(Object3D object3D){
        object3DList.add(object3D);
    }

    public List<Object3D> getObject3DList() {
        return object3DList;
    }

    public void setObject3DList(List<Object3D> object3DList) {
        this.object3DList = object3DList;
    }
}
