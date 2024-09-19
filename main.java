import commands.Run;
import src.DiscordWebhook;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

        DiscordWebhook dw = new DiscordWebhook("https://discord.com/api/webhooks/");

        Run run = new Run();
        String[] results = run.run();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < results.length; i++) {
            String[] result = urls(results[i]);
            if (result != null) {
                ArrayList<String> urls = new ArrayList<String>();
                for (int j = 0; j < result.length; j++) {
                    urls.add(result[j]);
                }
                list.add(urls);
            }
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm:ss");
        String strDate = formatter.format(date);
        // 取得完了・DiscordにURLを送信
        for (int i = 0; i < list.size(); i++) {
            ArrayList<String> urls = list.get(i);
            dw.addEmbed(new DiscordWebhook.EmbedObject().setTitle("twidouga.net-scraper").setDescription(strDate)
                    .setImage(urls.get(1)).setUrl(urls.get(0)));
            if(dw.embeds.size() == 10) {
                dw.execute();
                dw.embeds.clear();
                Thread.sleep(1000);
            }
        }

    }

    static String[] urls(String results) {
        Pattern pattern = Pattern.compile("'(.*)', '(.*)'");
        String[] returns = { "", "" };
        Matcher matcher = pattern.matcher(results);
        if (matcher.find()) {
            returns[0] = (matcher.group(1));
            returns[1] = (matcher.group(2));
        } else {
            return null;
        }
        return returns;
    }
}
