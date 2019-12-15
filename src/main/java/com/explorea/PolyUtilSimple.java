package com.explorea;

import com.explorea.model.SimpleDirectionsRoute;

import java.util.ArrayList;
import java.util.List;

public class PolyUtilSimple {

    public static List<SimpleDirectionsRoute.LatLng> decode(final String encodedPath) {
        int len = encodedPath.length();

        final List<SimpleDirectionsRoute.LatLng> path = new ArrayList<SimpleDirectionsRoute.LatLng>();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new SimpleDirectionsRoute.LatLng(lat * 1e-5, lng * 1e-5));
        }

        return path;
    }

    /**
     * Encodes a sequence of LatLngs into an encoded path string.
     */
    public static String encode(final List<SimpleDirectionsRoute.LatLng> path) {
        long lastLat = 0;
        long lastLng = 0;

        final StringBuffer result = new StringBuffer();

        for (final SimpleDirectionsRoute.LatLng point : path) {
            long lat = Math.round(point.lat * 1e5);
            long lng = Math.round(point.lng * 1e5);

            long dLat = lat - lastLat;
            long dLng = lng - lastLng;

            encode(dLat, result);
            encode(dLng, result);

            lastLat = lat;
            lastLng = lng;
        }
        return result.toString();
    }

    private static void encode(long v, StringBuffer result) {
        v = v < 0 ? ~(v << 1) : v << 1;
        while (v >= 0x20) {
            result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
            v >>= 5;
        }
        result.append(Character.toChars((int) (v + 63)));
    }

    public static String hexEncode(String decoded) {
        StringBuilder ret = new StringBuilder();
        char[] arr =  decoded.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            short s = (short) arr[i];
            if (s < 20 || s > 126) throw th("Cannot encode character at " +
                    i + ", not ASCII writing character");
            ret.append(String.format("%x", s));
        }
        return ret.toString();
    }

    public static String hexDecode(String encoded) {
        StringBuilder ret = new StringBuilder();
        if (encoded.length() % 2 != 0)
            throw th("String length illegal for percent-encoded String");
        String s;
        char c;
        for (int i = 0; i < encoded.length()/2; i++) {
            s = encoded.substring(i * 2, (i + 1) * 2);
            try {
                c = (char) Short.parseShort(s, 16);
            } catch (NumberFormatException ignore) {
                throw th("Cannot decode character at " + (i * 2) +
                        ", not ASCII hex value");
            }
            if (c < 20 || c > 126) throw th("Cannot encode character at " +
                    (i * 2) + ", not ASCII writing character");
            ret.append(c);
        }
        return ret.toString();
    }

    private static IllegalArgumentException th(String msg) {
        return new IllegalArgumentException(msg);
    }
}