package fr.atesab.xray.utils;

import java.util.Map;

import org.joml.Matrix4f;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderSetup.RenderSetupBuilder;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RenderUtils {
		public static final String SHADER_POSITION_TEX = "core/position_tex";
		public static final String SHADER_POSITION_COLOR = "core/position_color";
		public static final String SHADER_POSITION_TEX_COLOR = "core/position_tex_color";
		public static final String SHADER_LINES = "core/rendertype_lines";

	    
		public static enum Shaders {
			POSITION_TEX(SHADER_POSITION_TEX),
			POSITION_COLOR(SHADER_POSITION_COLOR),
			POSITION_TEX_COLOR(SHADER_POSITION_TEX_COLOR),
			LINES(SHADER_LINES);
			
			private String value;
			private Shaders(String keyString) {
				value = keyString;			}
		}

        public static void renderSingleLine(PoseStack stack, RenderingChannelBuilder builder, float x1, float y1, float z1,
                        float x2, float y2,
                        float z2, float r, float g, float b, float a, float lineWidth) {
                Vec3 normal = new Vec3(x2 - x1, y2 - y1, z2 - z1).normalize();
                renderSingleLine(stack, builder, x1, y1, z1, x2, y2, z2, r, g, b, a, (float) normal.x, (float) normal.y,
                                (float) normal.z, lineWidth);
        }

        public static void renderSingleLine(PoseStack stack, RenderingChannelBuilder builder, float x1, float y1, float z1,
                        float x2, float y2,
                        float z2, float r, float g, float b, float a, float normalX, float normalY, float normalZ, float lineWidth) {
                Matrix4f pose = stack.last().pose();
                //PoseStack.Pose matrix3f = stack.last();
                builder.addVertex(pose, x1, y1, z1).setColor(r, g, b, a).setLineWidth(lineWidth)
                                .setNormal(normalX, normalY, normalZ);
                builder.addVertex(pose, x2, y2, z2).setColor(r, g, b, a).setLineWidth(lineWidth)
                                .setNormal(normalX, normalY, normalZ);
        }

        // copied vanilla code for Sodium/Rubidium compatibility, because they overwrite and broke this vanilla code.
        // see net.minecraft.client.renderer.ShapeRenderer#renderLineBox
        public static void renderLineBoxVanillaStyle(PoseStack stack, RenderingChannelBuilder builder, AABB shape, float red, float green, float blue, float alpha, float lineWidth) {
        	renderLineBoxVanillaStyle(stack, builder, shape.minX, shape.minY, shape.minZ, shape.maxX, shape.maxY, shape.maxZ, red, green, blue, alpha, red, green, blue, lineWidth);
         }

        public static void renderLineBoxVanillaStyle(PoseStack stack, RenderingChannelBuilder builder, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha, float lineWidth) {
        	renderLineBoxVanillaStyle(stack, builder, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha, red, green, blue, lineWidth);
        }

        public static void renderLineBoxVanillaStyle(PoseStack stack, RenderingChannelBuilder builder, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red1, float green1, float blue1, float alpha, float shadowRed, float shadowGreen, float shadowBlue, float lineWidth) {
            Matrix4f pose = stack.last().pose();
            //PoseStack.Pose matrix3f = poseStack.last();
            float f = (float)minX;
            float f1 = (float)minY;
            float f2 = (float)minZ;
            float f3 = (float)maxX;
            float f4 = (float)maxY;
            float f5 = (float)maxZ;
            builder.addVertex(pose, f, f1, f2).setColor(red1, green1, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f2).setColor(red1, shadowGreen, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f, f1, f2).setColor(shadowRed, green1, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f, f4, f2).setColor(shadowRed, green1, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f, f1, f2).setColor(shadowRed, shadowGreen, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f, f1, f5).setColor(shadowRed, shadowGreen, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f3, f1, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(-1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(-1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, -1.0F, 0.0F);
            builder.addVertex(pose, f, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, -1.0F, 0.0F);
            builder.addVertex(pose, f, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, -1.0F);
            builder.addVertex(pose, f3, f1, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, -1.0F);
            builder.addVertex(pose, f, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f3, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
         }
        
        public static RenderType getLineRenderType(String label) {
        	return buildRenderType(
	        			label, 
	        			buildPipeline("pipeline/lines", RenderPipelines.LINES_SNIPPET, RenderUtils.Shaders.LINES, BlendFunction.TRANSLUCENT, CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH, VertexFormat.Mode.LINES, false, null),
	        			LayeringTransform.NO_LAYERING,
	        			OutputTarget.ITEM_ENTITY_TARGET
	        			);
        }

        public static RenderPipeline buildEntityPipeline(String location) {
        	return buildPipeline(location, RenderPipelines.MATRICES_FOG_SNIPPET, RenderUtils.Shaders.POSITION_COLOR, new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO), CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, null, null);
        }
        
        public static RenderPipeline buildGuiPipeline(String location) {
        	return buildPipeline(location, RenderPipelines.GUI_TEXTURED_SNIPPET, RenderUtils.Shaders.POSITION_TEX, BlendFunction.TRANSLUCENT, CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, null, null);
        }
        
        public static RenderType getColoredGuiRenderType(String label) {
        	return buildRenderType(
        				label,
        				buildPipeline("pipeline/gui_textured", RenderPipelines.GUI_TEXTURED_SNIPPET, RenderUtils.Shaders.POSITION_TEX_COLOR, BlendFunction.TRANSLUCENT, CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, null, null),
        				null,
        				null
        				);
        }
        
        public static RenderType buildRenderType(String label, RenderPipeline pipeline, LayeringTransform layerTransform, OutputTarget outputTarget) {
        	RenderSetupBuilder builder = RenderSetup.builder(pipeline);
        	if (layerTransform != null) {
        		builder.setLayeringTransform(layerTransform);
        	}
        	if (outputTarget != null) {
    			builder.setOutputTarget(outputTarget);
        	}
        	return RenderType.create(label,builder.createRenderSetup());       
        }
        
        public static RenderPipeline buildPipeline(String location, RenderPipeline.Snippet base, RenderUtils.Shaders shader, BlendFunction blendFunction, CompareOp depthFunction, VertexFormat defaultFormat, VertexFormat.Mode defaultmode, Boolean isCull, Map<String, UniformType> uniformMap) {
        	RenderPipeline.Builder builder = RenderPipeline.builder(base);
        		builder.withLocation(location);
        	if (shader != null) {
    			builder.withFragmentShader(shader.value).withVertexShader(shader.value);
        	}
        	if (blendFunction != null) {
    			builder.withColorTargetState(new ColorTargetState(blendFunction));
        	}
        	if (depthFunction != null) {
        		builder.withDepthStencilState(new DepthStencilState(depthFunction, false));
        	}
        	if (uniformMap != null) {
        		for (String uniformKey : uniformMap.keySet()) {
        			builder.withUniform(uniformKey, uniformMap.get(uniformKey));
        		}
        	}
        	if (isCull != null) {
        		builder.withCull(isCull);
        	}
    		return builder.withVertexFormat(defaultFormat, defaultmode).build();
        }
        
        /*
        public static RenderSystem.AutoStorageIndexBuffer buildBuffer(VertexFormat.Mode mode) {
        	return RenderSystem.getSequentialBuffer(mode);
        }
        */

        /*
        public static GpuTexture getGpuTexture(Identifier location, TextureFormat format) {
        	return RenderSystem.getDevice().createTexture(location.getNamespace(),GpuTexture.USAGE_COPY_SRC, format, 0, 0, 0 , 0);
        }
        public static void renderIfExists(RenderPipeline usingPipeLine, RenderSystem.AutoStorageIndexBuffer autoStorageBuffer) {
        	renderIfExists(usingPipeLine, autoStorageBuffer, 72);
        }
       
        public static void renderIfExists(RenderPipeline usingPipeLine, RenderSystem.AutoStorageIndexBuffer autoStorageBuffer, int bufferSize) {
            GpuBuffer gpuBuffer = RenderSystem.getDevice()
                    .createBuffer(() -> "vertext simple buffer", GpuBuffer.USAGE_MAP_WRITE, bufferSize);
    		RenderTarget renderTarget = Minecraft.getInstance().getMainRenderTarget();
            try (RenderPass renderpass = RenderSystem.getDevice()
                    .createCommandEncoder()
                    .createRenderPass(() -> "vertext simple RenderPass", renderTarget.getColorTextureView(), OptionalInt.empty(), renderTarget.getDepthTextureView(), OptionalDouble.empty())) {
                renderpass.setPipeline(usingPipeLine);
                renderpass.setIndexBuffer(autoStorageBuffer.getBuffer(36), autoStorageBuffer.type());
                renderpass.setVertexBuffer(0, gpuBuffer);
                renderpass.drawIndexed(0, 0, 0, 0);
            }
        }
        	
        public static void renderIfExists(BufferBuilder buffer, RenderPipeline usingPipeLine, RenderSystem.AutoStorageIndexBuffer autoStorageBuffer) {
    		MeshData mesh = buffer.build();
    		if (mesh != null) {
    			//GpuBuffer vertexBuffer = RenderSystem.getDevice()
    			//		.createBuffer(() -> "vertex buffer", GpuBuffer.USAGE_MAP_WRITE, mesh.vertexBuffer());
    			GpuBuffer vertexBuffer =
    					usingPipeLine.getVertexFormat().uploadImmediateVertexBuffer(mesh.vertexBuffer());

    			RenderTarget renderTarget = Minecraft.getInstance().getMainRenderTarget();
    			GpuTextureView texture = renderTarget.getColorTextureView();
    			int drawStateCount = mesh.drawState().indexCount();
    			if (texture == null || drawStateCount == 0) {
    				return;
    			}

				Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
				matrix4fStack.pushMatrix();

    			GpuBuffer gpuBuffer = autoStorageBuffer.getBuffer(drawStateCount);
    			try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder()
    					.createRenderPass(() -> "vertex rendePass", texture, OptionalInt.empty(), renderTarget.getDepthTextureView(), OptionalDouble.empty())) {

    				renderPass.setPipeline(usingPipeLine);
    				RenderSystem.bindDefaultUniforms(renderPass);
    				renderPass.setIndexBuffer(gpuBuffer, autoStorageBuffer.type());
    				renderPass.setVertexBuffer(0, vertexBuffer);
    				renderPass.drawIndexed(0, 0, drawStateCount, 0);

    			}
    			
				matrix4fStack.popMatrix();
    			mesh.close();
    		}
        }
         */

        public static float getScaleValue(int sourceValue, int targetValue) {
        	return ((float)(Math.ceil(targetValue * 10 / sourceValue)) / 10);
        }
        
        private RenderUtils() {
        }
}
