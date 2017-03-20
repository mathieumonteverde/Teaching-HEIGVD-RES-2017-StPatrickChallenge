package ch.heigvd.res.stpatrick;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * This class is responsible for providing different types of Stream Processors.
 *
 * @author Olivier Liechti
 */
public class StreamProcessorsFactory implements IStreamProcessorsFactory {

   @Override
   public IStreamProcessor getProcessor() {
      return new BasicStreamProcessor();
   }

   @Override
   public IStreamProcessor getProcessor(String processorName) throws UnknownNameException {
      if (processorName.equals("e-remover")) {
         return new IStreamProcessor() {
            @Override
            public void process(Reader in, Writer out) throws IOException {
               BufferedReader br = new BufferedReader(in);
               BufferedWriter bw = new BufferedWriter(out);
               int c = br.read();
               while (c != -1) {
                  if (c != 'e' && c != 'E')
                     bw.write(c);
                  c = br.read();
               }
               bw.flush();
            }
         };
      } else {
         return getProcessor();
      }
   }

}
