package ru.otr.iprange;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPRange {

    public static void printRange(final String ip1, final String ip2) {
        if (ip1 == null || ip1.isEmpty() || ip2 == null || ip2.isEmpty()) {
            System.err.println("Введенные ip-адреса некорректны");
        }

        long start = 0;
        long end = 0;
        try {
            start = ipToLong(InetAddress.getByName(ip1));
            end = ipToLong(InetAddress.getByName(ip2));

        } catch (UnknownHostException e) {
            System.err.println("Введенные ip-адреса некорректны");
        }

        if (end < start) {
            long tmp = end;
            end = start;
            start = tmp;
        }

        while (start+1 < end) {
            System.out.println(longToIp(start+1));
            start++;
        }
    }


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
