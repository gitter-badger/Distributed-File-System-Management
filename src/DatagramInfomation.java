import java.net.InetAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class DatagramInfomation {
    private String message;
    private InetAddress address;
    private int port;
}
