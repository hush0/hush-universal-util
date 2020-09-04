import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IPaddress {
  
   public static String getServerIP() throws Exception{
    //获得上平台机构清单
    String ip = InetAddress.getLocalHost().getHostAddress();
    System.out.println("第一次获取的IP" + ip);
    if ("127.0.0.1".equals(ip)) {
      // 读取服务器网卡信息
      String temp = "";
      Enumeration<NetworkInterface> netInterfaces = NetworkInterface
          .getNetworkInterfaces();
      InetAddress localhost = null;
      while (netInterfaces.hasMoreElements()) {
        NetworkInterface ni = (NetworkInterface) netInterfaces
            .nextElement();
        System.out.println(ni.getName());
        Enumeration<InetAddress> addresses=ni.getInetAddresses();
        while(addresses.hasMoreElements()){
        localhost=addresses.nextElement();
        System.out.println("localhost.isSiteLocalAddress():"
            + localhost.isSiteLocalAddress());
        System.out.println("localhost.isLoopbackAddress():"
            + localhost.isLoopbackAddress());
        System.out.println("localhost.getHostAddress():"
            + localhost.getHostAddress());
        if (localhost.isSiteLocalAddress()
            && !localhost.isLoopbackAddress()
            && localhost.getHostAddress().indexOf(":") == -1) {
          temp = localhost.getHostAddress();
          break;
        }
              }
        if(temp!=null && !("".equals(temp))){
          ip=temp;
          break;
        }
      }
      System.out.println("通过网卡获取的地址" + temp);
      // 读取Linex IP配置文件
      if (temp == null || "".equals(temp) || "127.0.0.1".equals(ip)) {
        String url = "/etc/sysconfig/network-scripts/ifcfg-eth0";

        try {
          BufferedReader br = new BufferedReader(new FileReader(
              url));
          String line = "";
          // IPADDR=
          while ((line = br.readLine()) != null) {
            if (line.indexOf("IPADDR") != -1) {
              line = line.substring(line.indexOf("=") + 1,
                  line.length()).trim();
              System.out.println("读服务器IP文件IP=【" + line + "】");
              temp = line;
            }
          }
        } catch (Exception ex) {
          System.out.println("读服务器IP文件错误！");
        }
        System.out.println("读配置文件获取的地址" + temp);
      }
      ip = temp;
    }
    return ip;
  }
}
