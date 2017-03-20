package ch.heigvd.res.stpatrick;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;


/**
 * 
 * @author Olivier Liechti
 */
public class Application {

  IStreamProcessorsFactory processorsFactory = new StreamProcessorsFactory();
  
  public IStreamProcessorsFactory getStreamProcessorsFactory() {
    return processorsFactory;
  }

  IStreamDecoratorController getStreamDecoratorController() {
    return new IStreamDecoratorController() {
       @Override
       public Reader decorateReader(Reader inputReader) {
          return inputReader;
       }

       @Override
       public Writer decorateWriter(Writer outputWriter) {
          
          return new Writer() {
             private Writer wr = outputWriter;
             
             @Override
             public void close() throws IOException {
                wr.close();
             }
             
             @Override
             public void flush() throws IOException{
                wr.flush();
             }

             @Override
             public void write(char[] cbuf, int off, int len) throws IOException {
                
                // Create a new buffer
                char[] filteredBuf = new char[cbuf.length];
                int j = 0;
                
                // length used to correct the final length of the buffer
                int finalLen = len;
                
                // Copy everything that is not an 'A' or an 'a'
                for (int i = off; i < len; ++i) {
                   if (cbuf[i] != 'a' && cbuf[i] != 'A') {
                      filteredBuf[j] = cbuf[i];
                      ++j;
                   } else {
                      finalLen--;
                   }                
                }
                
                wr.write(filteredBuf, off, finalLen);
             }

             @Override
             public void write(int c) throws IOException {
                System.out.println(c);
                
                if (c != 'a' && c != 'A')
                   wr.write(c);
             }
          };
          
       }
    };
  }
}
