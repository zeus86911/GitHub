package com.xxx.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imageAction")
public class ImageAction {

	@RequestMapping(value = "initImage")
	public void initImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String imagePath = request.getParameter("imagePath");
		InputStream inStream = new FileInputStream(imagePath);
		// 得到图片的二进制数据
		byte[] data = readInputStream(inStream);
		// 写入数据
		OutputStream os = response.getOutputStream();
		os.write(data);
		// 关闭流
		os.flush();
		inStream.close();
		os.close();
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭流
		outStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	/**
	 * 文件下载
	 * 
	 */
	public void downFile(HttpServletResponse response, String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			InputStream ins = new FileInputStream(path);
			BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
			OutputStream outs = response.getOutputStream();// 获取文件输出IO流 ;
			try {
				response.setContentType("application/zip");// 设置response内容的类型
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8").replace("+", "%20"));// 设置头部信息
				int len = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((len = bins.read(buffer, 0, 8192)) != -1) {
					outs.write(buffer, 0, len);
				}
				// 这里一定要调用flush()方法
				outs.flush();
				ins.close();
				bins.close();
				outs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(" 下载异常：" + e.getMessage());
			} finally {
				if (ins != null) {
					ins.close();
				}
				if (bins != null) {
					bins.close();
				}
				if (outs != null) {
					outs.close();
				}
			}
		} else {
			return;
		}
	}
	
	public void downFile(HttpServletResponse response, String path, String fileName) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			InputStream ins = new FileInputStream(path);
			BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
			OutputStream outs = response.getOutputStream();// 获取文件输出IO流 ;
			BufferedOutputStream bouts = new BufferedOutputStream(outs); ;
			try {
				response.setContentType("application/zip");// 设置response内容的类型
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20"));// 设置头部信息
				int len = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((len = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, len);
				}
				// 这里一定要调用flush()方法
				bouts.flush();
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			} catch (Exception e) {
				System.out.println(" 下载异常：" + e.getMessage());
			} finally {
				if (ins != null) {
					ins.close();
				}
				if (bins != null) {
					bins.close();
				}
				if (outs != null) {
					outs.close();
				}
				if (bouts != null) {
					bouts.close();
				}
			}
		} else {
			return;
		}
	}
	
}
