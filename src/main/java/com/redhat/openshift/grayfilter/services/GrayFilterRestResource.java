package com.redhat.openshift.grayfilter.services;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@RestController
@RequestMapping("/grayfilters")
public class GrayFilterRestResource {

	public GrayFilterRestResource() {

	}

	/**
	 * Converts a base64 Image String to a Gray Scale (Disabled) Image
	 * 
	 * @param base64ImgString
	 * @return
	 */
	@Nullable
	@RequestMapping(method = RequestMethod.POST)
	public String applyGrayFilter(@NotNull @RequestBody String base64ImgString) {
		try {

			final BASE64Decoder decoder = new BASE64Decoder();
			final byte[] imageByte = decoder.decodeBuffer(base64ImgString);
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByte);
			final BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

			final Image grayImage = GrayFilter.createDisabledImage(bufferedImage);

			return encodeToString(toBufferedImage(grayImage), "PNG");

		} catch (Exception e) {
			System.err.println(e);
		}

		return null;
	}

	private BufferedImage toBufferedImage(Image image) {
		Preconditions.checkNotNull(image);
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.drawImage(image, 0, 0, null);
		graphics2D.dispose();

		return bufferedImage;
	}

	private String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, byteArrayOutputStream);
			final byte[] imageBytes = byteArrayOutputStream.toByteArray();

			final BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);

			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

}