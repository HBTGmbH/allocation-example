package de.hbt.cfa.tooling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class T3_DeleteCopyCutPaste {
    @Test
    void deleteDuplicateCopyCutLine() {
        //Press Ctrl-Y to delete this comment line
        //Press Ctrl-D to duplicate this comment line
        //Press Ctrl-C to copy this comment line
        //Press Ctrl-X to cut this comment line
    }

    @Test
    void extendDecreaseTheSelectionScope() {
        assertThat(1).isEqualTo(1); //place the cursor somewhere in this line
        //Press Ctrl-W multiple times to extend the selection scope
        //Press Ctrl-Shift-W multiple times to decrease the selection scope
    }

    public class CopyPasteClassIntoPackage {
        //select and copy this class block and press Ctrl-V in the project explorer to paste it into a package
    }
}
