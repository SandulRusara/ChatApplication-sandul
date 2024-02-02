package lk.ijse.Sinhala;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SinhalaTyping extends VBox {

    private ListView<String> sinhalaWordsView;

    public SinhalaTyping(){
        List<String> sinhala = new ArrayList<>();

        String[] sinhalaCharacter = new String[]{String.valueOf('\u0D85'), String.valueOf('\u0D86'),
                    String.valueOf('\u0D87'), String.valueOf('\u0D88'), String.valueOf('\u0D89'),
                    String.valueOf('\u0D90'), String.valueOf('\u0D91'), String.valueOf('\u0D92'),
                    String.valueOf('\u0D94'), String.valueOf('\u0D95'), String.valueOf('\u0D96'),
                    String.valueOf('\u0D9C'), String.valueOf('\u0D9A'), String.valueOf('\u0DCF'),
                    String.valueOf('\u0DDC'), String.valueOf('\u0D83'), String.valueOf(Character.valueOf((char)0x0D9A)),
                    String.valueOf(Character.valueOf((char) 0x0D9B)), String.valueOf(Character.valueOf((char) 0x0D9A)), String.valueOf(Character.valueOf((char)0x0DCF)), String.valueOf(Character.valueOf((char)0x0D9B)),
                    String.valueOf(Character.valueOf((char)0x0DCF)), String.valueOf(Character.valueOf((char)0x0D9A)), String.valueOf(Character.valueOf((char)0x0DD0)), String.valueOf(Character.valueOf((char)0x0D9B)),
                    String.valueOf(Character.valueOf((char)0x0D9A)), String.valueOf(Character.valueOf((char)0x0DD1)), String.valueOf(Character.valueOf((char)0x0D9B)), String.valueOf(Character.valueOf((char)0x0DD1)),
                    String.valueOf(Character.valueOf((char)0x0D9A)), String.valueOf(Character.valueOf((char)0x0DD2)), String.valueOf(Character.valueOf((char)0x0D9B)), String.valueOf(Character.valueOf((char)0x0DD2)),
                    String.valueOf(Character.valueOf((char)0x0D9A)), String.valueOf(Character.valueOf((char)0x0DD3)), String.valueOf(Character.valueOf((char)0x0D9B)), String.valueOf(Character.valueOf((char)0x0DD3)),
                    String.valueOf(Character.valueOf((char)0x0D9A)), String.valueOf(Character.valueOf((char)0x0DD4)), String.valueOf(Character.valueOf((char)0x0D9B)), String.valueOf(Character.valueOf((char)0x0DD4)),
                    String.valueOf(Character.valueOf((char)0x0D9A)), String.valueOf(Character.valueOf((char)0x0DD6)), String.valueOf(Character.valueOf((char)0x0D9B)), String.valueOf(Character.valueOf((char)0x0DD6)),
                    String.valueOf(Character.valueOf((char)0x0DAF)), String.valueOf(Character.valueOf((char)0x0DB6)), String.valueOf(Character.valueOf((char)0x0DB4)), String.valueOf(Character.valueOf((char)0x0DB8)),
                    String.valueOf(Character.valueOf((char)0x0DC0)), String.valueOf(Character.valueOf((char)0x0DC3)), String.valueOf(Character.valueOf((char)0x0DC4)), String.valueOf(Character.valueOf((char)0x0DBA)),
                    String.valueOf(Character.valueOf((char)0x0DBB)), String.valueOf(Character.valueOf((char)0x0DB1)), String.valueOf(Character.valueOf((char)0x0DBD)), String.valueOf(Character.valueOf((char)0x0DC5)),
                    String.valueOf(Character.valueOf((char)0x0DA2)), String.valueOf(Character.valueOf((char)0x0DC1)), String.valueOf(Character.valueOf((char)0x0DC2)),String.valueOf(Character.valueOf((char)0x0DD0))

        };

        for (String sinhalaChar : sinhalaCharacter){
            sinhala.add(sinhalaChar);
        }

        sinhalaWordsView = new ListView<>();
        sinhalaWordsView.setItems(FXCollections.observableArrayList(sinhala));

        HBox hBox = new HBox(sinhalaWordsView);
        hBox.setPadding(new Insets(10));

        getChildren().add(hBox);
    }

    public ListView<String> getSinhalaWordsView(){
        return sinhalaWordsView;
    }
}
