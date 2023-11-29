package lk.ijse.controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;

public class TestController {
    public ImageView docImage;

    public Text txtName;
    public Text txtDescription;
    public Button btnSelect;

    public byte image[];
    public void setDocImage(byte[] docImage) {
        image=docImage;
        Image image = new Image(new ByteArrayInputStream(docImage));
        this.docImage.setImage(image);
        this.docImage.setPreserveRatio(false);
    }

    public void setTxtName(String txtName) {
        this.txtName.setText(txtName);
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription.setText(txtDescription);
    }

    public Button getBtnSelect() {
        return btnSelect;
    }

    public String  getTxtName() {
        return txtName.getText();
    }

    public byte[] getDocImage() {
        return image;
    }

    public String getTxtDescription() {
        return txtDescription.getText();
    }
}
