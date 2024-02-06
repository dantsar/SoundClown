package SoundClown;

import java.io.IOException;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.RunProcessFunction;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to SoundClown, the circus of sound!!!!!!!!!!!!!!!");

        try {
            FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
            FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");

            FFmpegBuilder builder = new FFmpegBuilder()
                .setInput("/code/test_audio/audio_files/amen_beasty.wav")     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true) // Override the output if it exists
                .addOutput("/code/test_audio/audio_files/BEST_AMEN_BREAK.mp3")   // Filename for the destination
                    .setAudioCodec("libmp3lame") // using the LAME MP3 encoder
                    .setAudioBitRate(128000) // Set the audio bitrate to 128 kbit/s
                    .setAudioChannels(2) // Set the number of audio channels
                    .setAudioSampleRate(44100) // Set the audio sample rate to 44.1 kHz
                .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            // Run a one-pass encode
            executor.createJob(builder).run();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}


