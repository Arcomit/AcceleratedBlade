package mod.arcomit.slashblade_acceleratedrendering.mixin;

import mods.flammpfeil.slashblade.client.renderer.SlashBladeTEISR;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * Mixin配置插件，用于检测acceleratedrendering模组及其版本
 * @author Arcomit
 */
public class MixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        ModFileInfo acceleratedRenderingInfo = LoadingModList.get().getModFileById("acceleratedrendering");
        ModFileInfo slashBladeInfo = LoadingModList.get().getModFileById("slashblade");
        if (acceleratedRenderingInfo == null || slashBladeInfo == null) {
            return false;
        }

        // 获取版本号
        String version = slashBladeInfo.getMods().get(0).getVersion().toString();
        // 检查版本是否在1.0.1到1.0.7之间
        return isVersionInRange(version, "0.1.4", "1.8.56");
    }

    /**
     * 检查版本是否在指定范围内（包含边界）
     * @param version 当前版本
     * @param minVersion 最小版本
     * @param maxVersion 最大版本
     * @return 是否在范围内
     */
    private boolean isVersionInRange(String version, String minVersion, String maxVersion) {
        try {
            // 移除版本号中可能存在的前缀（如 "1.0.7-1.20.1-alpha"）
            String cleanVersion = version.split("-")[0];

            return compareVersion(cleanVersion, minVersion) >= 0
                && compareVersion(cleanVersion, maxVersion) <= 0;
        } catch (Exception e) {
            // 如果版本解析失败，为了安全起见返回false
            System.err.println("Failed to parse acceleratedrendering version: " + version);
            return false;
        }
    }

    /**
     * 比较两个版本号
     * @param version1 版本1
     * @param version2 版本2
     * @return 如果version1 > version2返回正数，相等返回0，小于返回负数
     */
    private int compareVersion(String version1, String version2) {
        String[] v1Parts = version1.split("\\.");
        String[] v2Parts = version2.split("\\.");

        int maxLength = Math.max(v1Parts.length, v2Parts.length);

        for (int i = 0; i < maxLength; i++) {
            int v1Part = i < v1Parts.length ? Integer.parseInt(v1Parts[i]) : 0;
            int v2Part = i < v2Parts.length ? Integer.parseInt(v2Parts[i]) : 0;

            if (v1Part != v2Part) {
                return v1Part - v2Part;
            }
        }

        return 0;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

}
