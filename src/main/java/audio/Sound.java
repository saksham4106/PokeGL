package audio;

import game.Window;
import org.lwjgl.BufferUtils;
import utils.Assets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_memory;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Sound {
    private int bufferId;
    private int sourceId;
    public String filepath;

    private boolean isPlaying = false;

    public Sound(String filepath, boolean loops) {
        this.filepath = filepath;

        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);
        ShortBuffer rawAudioBuffer;
        try {
            byte[] soundData = Assets.readFileAsInputStream(filepath).readAllBytes();
            ByteBuffer soundBuffer = BufferUtils.createByteBuffer(soundData.length);
            soundBuffer.put(soundData);
            soundBuffer.flip();
            rawAudioBuffer = stb_vorbis_decode_memory(soundBuffer, channelsBuffer, sampleRateBuffer);
        } catch (IOException e) {
            rawAudioBuffer =
                    stb_vorbis_decode_filename(filepath, channelsBuffer, sampleRateBuffer);
        }

        if (rawAudioBuffer == null) {
            Window.LOGGER.error("Could not load sound '" + filepath + "'");
            stackPop();
            stackPop();
            return;
        }

        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();

        stackPop();
        stackPop();

        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        bufferId = alGenBuffers();
        alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

        sourceId = alGenSources();

        alSourcei(sourceId, AL_BUFFER, bufferId);
        alSourcei(sourceId, AL_LOOPING, loops ? 1 : 0);
        alSourcei(sourceId, AL_POSITION, 0);
        alSourcef(sourceId, AL_GAIN, 0.3f);

        free(rawAudioBuffer);
    }

    public void delete() {
        alDeleteSources(sourceId);
        alDeleteBuffers(bufferId);
    }

    public void play() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
            alSourcei(sourceId, AL_POSITION, 0);
        }

        if (!isPlaying) {
            alSourcePlay(sourceId);
            isPlaying = true;
        }
    }

    public void stop() {
        if (isPlaying) {
            alSourceStop(sourceId);
            isPlaying = false;
        }
    }

    public boolean isPlaying() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
        }
        return isPlaying;
    }
}
