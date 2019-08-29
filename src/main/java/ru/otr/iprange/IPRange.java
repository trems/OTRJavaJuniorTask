package ru.otr.iprange;

import java.net.InetAddress;

public class IPRange {


    private static long ipToLong(final InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }


    private static String longToIp(long ip) {
        StringBuilder ipStringBuilder = new StringBuilder(15);

        for (int i = 0; i < 4; i++) {
            ipStringBuilder.insert(0, (ip & 0xff));

            if (i < 3) {
                ipStringBuilder.insert(0,'.');
            }

            ip = ip >> 8;
        }
        return ipStringBuilder.toString();
    }

}
