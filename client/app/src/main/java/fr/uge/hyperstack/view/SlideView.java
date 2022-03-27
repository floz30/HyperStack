package fr.uge.hyperstack.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.ElementVisitor;
import fr.uge.hyperstack.model.PaintElement;
import fr.uge.hyperstack.model.Text;
import fr.uge.hyperstack.model.media.Image;
import fr.uge.hyperstack.model.media.Sound;
import fr.uge.hyperstack.model.media.Video;

public class SlideView extends ConstraintLayout implements ElementVisitor {
    private final Context context;
    private final ConstraintSet constraintSet = new ConstraintSet();
    private final EditorView editorView;


    public SlideView(@NonNull Context context, ConstraintLayout layout) {
        super(context);
        this.context = context;
        this.editorView = new EditorView(context);
        editorView.setId(ViewCompat.generateViewId());
        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(editorView);
        init(context, layout);
    }

    private void init(Context context, ConstraintLayout layout) {
        this.setId(ViewCompat.generateViewId());
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * EXEMPLE D'UTILISATION
     */
    public void drawButton() {
        Button btn = new Button(context);
        btn.setId(ViewCompat.generateViewId());
        btn.setText("HELLO");

        constraintSet.connect(btn.getId(), ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(btn.getId(), ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(btn.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(btn.getId(), ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(btn.getId(), 0.75f);
        constraintSet.setVerticalBias(btn.getId(), 0.25f);
        constraintSet.constrainWidth(btn.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(btn.getId(), ConstraintSet.WRAP_CONTENT);

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(btn);
    }

    public void build() {
        this.setConstraintSet(constraintSet);
    }

    @Override
    public void draw(Image image) {
        ImageView img = new ImageView(context);

        img.setId(ViewCompat.generateViewId());
        img.setImageBitmap(image.getContent());
        int id = img.getId();

        constraintSet.connect(id, ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(id, ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(id, ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(id, image.convertBiasHorizontal(this.getWidth()));
        constraintSet.setVerticalBias(id, image.convertBiasVertical(this.getHeight()));
        constraintSet.constrainWidth(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainMaxWidth(id, ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainMaxHeight(id, ConstraintSet.MATCH_CONSTRAINT);

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(img);
    }

    @Override
    public void draw(Video video) {
        VideoView videoView = new VideoView(context);
        videoView.setId(ViewCompat.generateViewId());
        videoView.setVideoURI(video.getLocation());

        MediaController mediaController = new MediaController(context);
        mediaController.setId(ViewCompat.generateViewId());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        FrameLayout videoFrame = new FrameLayout(context);
        videoFrame.setId(ViewCompat.generateViewId());
        videoFrame.addView(videoView);

        int id = videoFrame.getId();

        constraintSet.connect(id, ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(id, ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(id, ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(id, video.convertBiasHorizontal(this.getWidth()));
        constraintSet.setVerticalBias(id, video.convertBiasVertical(this.getHeight()));
        constraintSet.constrainWidth(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainMaxWidth(id, ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainMaxHeight(id, ConstraintSet.MATCH_CONSTRAINT);

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(videoFrame);
    }

    @Override
    public void draw(Sound sound) {
        Button btn = new Button(context);
        Drawable pause = AppCompatResources.getDrawable(context, R.drawable.ic_pause);
        Drawable play = AppCompatResources.getDrawable(context, R.drawable.ic_play);

        btn.setText(sound.getName());
        btn.setId(ViewCompat.generateViewId());
        int id = btn.getId();

        constraintSet.connect(id, ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(id, ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(id, ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(id, sound.convertBiasHorizontal(this.getWidth()));
        constraintSet.setVerticalBias(id, sound.convertBiasVertical(this.getHeight()));
        constraintSet.constrainWidth(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(id, ConstraintSet.WRAP_CONTENT);


        btn.setCompoundDrawablesWithIntrinsicBounds(play, null, null, null);
        btn.setOnClickListener(view -> {
            if (sound.isPlaying()) {
                sound.pauseSound();
                btn.setCompoundDrawablesWithIntrinsicBounds(play, null, null, null);
            } else {
                sound.playSound();
                btn.setCompoundDrawablesWithIntrinsicBounds(pause, null, null, null);
            }
        });

        sound.getPlayer().setOnCompletionListener(mediaPlayer -> {
            btn.setCompoundDrawablesWithIntrinsicBounds(play, null, null, null);
        });

        ViewGroup viewGroup = (ViewGroup) this;
        viewGroup.addView(btn);
    }

    @Override
    public void draw(PaintElement paintElement) {
//        editorView.addElement(paintElement);
//        System.out.println(editorView.getStrokeStack().size());
        int id = editorView.getId();
        constraintSet.connect(id, ConstraintSet.START, this.getId(), ConstraintSet.START);
        constraintSet.connect(id, ConstraintSet.END, this.getId(), ConstraintSet.END);
        constraintSet.connect(id, ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.constrainWidth(id, ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(id, ConstraintSet.WRAP_CONTENT);
    }

    @Override
    public void draw(Text text) {

    }

    public EditorView getEditorView(){
        return editorView;
    }


    public void clear() {
        editorView.clear();
        ViewGroup vg = this;
        vg.removeAllViews();
        vg.addView(editorView);
    }

}
