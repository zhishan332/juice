import com.juice.core.Juice;
import com.juice.core.config.ConfigLoader;
import com.juice.core.config.Template;
import com.juice.core.config.TemplateFactory;
import com.juice.core.http.DownLoader;
import com.juice.core.http.JuiceDownLoader;
import com.juice.core.pojo.DetailPage;
import com.juice.core.pojo.ListPage;
import com.juice.parse.JsoupHtmlParser;
import com.juice.parse.util.UUIDGenerator;
import org.junit.Test;

import java.util.List;

/**
 * 最直接的测试运行
 *
 * @author wangqing
 * @since 15-2-3 下午5:07
 */
public class TestRun {


    @Test
    public void testDiandian() throws Exception {
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.load();
        Juice juice = new Juice();
        List<Template> list = TemplateFactory.getInstance().getTemplateList();
        for (Template template : list) {
            System.out.println("正在分析："+template.getTemplateName());
            ListPage web = juice.getListPage(template.getFirstUrl(), template, new JsoupHtmlParser(), 0);
            System.out.println("web:" + web);
            for (String url : web.getUrls()) {
                DetailPage conWEB = juice.getDetailPage(url, template, new JsoupHtmlParser());
                System.out.println("con:" + conWEB);
                DownLoader downLoader = new JuiceDownLoader(2);
                if (conWEB.getImageList() != null) {
                    for (String img : conWEB.getImageList()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("D:\\usr\\");
                        sb.append(UUIDGenerator.get());
                        sb.append(".");
                        sb.append("jpg");
                        downLoader.downLoad(img, sb.toString(), 30);
                    }
                }
            }
        }
    }

}
