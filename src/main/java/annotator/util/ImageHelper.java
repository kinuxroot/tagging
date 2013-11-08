package annotator.util;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import javax.swing.ImageIcon;

import annotator.controller.CurrentImages;
import annotator.model.main.ImageModel;

import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;

/**
 * @author Brandon B. Lin
 * 
 */
public class ImageHelper {

	public static ImageModel getCurrentImageModel() {
		CurrentImages ci = CurrentImages.getInstance(null);
		int index = ci.getCurrent();
		return ci.getImageModelByIndex(index);
	}

	public static Image createIconImages(String path) {
		Image image = null;
		try {
			image = ImageIO.read(new File(path)).getScaledInstance(15, 15,
					Image.SCALE_DEFAULT);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public static ImageIcon getImageIcon(String path, int width, int height) {
		Image image = null;
		try {
			image = readTif(path, width, height);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't load image, check the path please");

		}
		return new ImageIcon(image);
	}

	private static Image readTif(String path, int width, int height) {
		FileInputStream in;
		Image imageScaled = null;
		try {
			in = new FileInputStream(path);
			FileChannel channel = in.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
			channel.read(buffer);
			Image image = load(buffer.array());
			imageScaled = image.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageScaled;

	}

	private static Image load(byte[] data) throws Exception {
		Image image = null;
		SeekableStream stream = new ByteArraySeekableStream(data);
		String[] names = ImageCodec.getDecoderNames(stream);
		ImageDecoder dec = ImageCodec
				.createImageDecoder(names[0], stream, null);
		RenderedImage im = dec.decodeAsRenderedImage();
		image = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
		return image;
	}

}
