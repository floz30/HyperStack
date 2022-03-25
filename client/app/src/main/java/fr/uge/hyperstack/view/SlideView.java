package fr.uge.hyperstack.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.media.Image;

public class SlideView extends ConstraintLayout {
    private int width = LayoutParams.MATCH_PARENT;
    private int height = LayoutParams.MATCH_PARENT;
    private Context context;
    //private View view;
    private LayoutInflater inflater;

    public SlideView(@NonNull Context context, ConstraintLayout layout) {
        super(context);
        this.context = context;
        init(context, layout);
    }

    private void init(Context context, ConstraintLayout layout) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.layer, null);

//        view = new View(context);
//        view.setId(ViewCompat.generateViewId());

        setId(ViewCompat.generateViewId());
//        setLayoutParams(new LayoutParams(width, height));
        ConstraintSet set = new ConstraintSet();
        set.connect(this.getId(), ConstraintSet.START, R.id.editLayout, ConstraintSet.START, 5);
        set.connect(this.getId(), ConstraintSet.END, R.id.editLayout, ConstraintSet.END, 5);
        set.connect(this.getId(), ConstraintSet.TOP, R.id.editLayout, ConstraintSet.TOP, 200);
        set.connect(this.getId(), ConstraintSet.BOTTOM, R.id.editLayout, ConstraintSet.BOTTOM, 200);
        set.applyTo(layout);

    }

    public void drawImage() {
//        ImageView imageView = new ImageView(context);
        Button btn = new Button(context);
        btn.setId(ViewCompat.generateViewId());
        btn.setText("ALLO");
        btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        btn.layout
//
        ConstraintSet set = new ConstraintSet();
        set.connect(btn.getId(), ConstraintSet.START, this.getId(), ConstraintSet.START, 50);
        set.connect(btn.getId(), ConstraintSet.END, this.getId(), ConstraintSet.END, 5);
        set.connect(btn.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP, 50);
        set.connect(btn.getId(), ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM, 5);

        set.applyTo(this);

//        this.addView(btn);
        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(btn);
    }

}
