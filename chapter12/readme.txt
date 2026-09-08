Note that this chapter builds on EGCL
To run the EGCL compiler, use egcl.Main in MyRVTool folder (this is equivalent to the solution from Chapter 6)

Note: unlike Chapter 6, this chapter doesn't ship a script/properties.egcl --
writing one (and having EGCLScript.toAspectJOffline() compile it into
monitoredfits/offline/Logger.aj) is part of this exercise, so egcl.Main has
nothing to read until you do that.

To run the FiTS system, use fits.Main

To run the player use player.Main in the Offline folder. Run fits.Main
first (with Logger.aj woven in) so it writes a log to log/ in this chapter's
own folder, then run player.Main to replay it. Note that the Logger.aj
provided here writes a single log/log.txt, while player.Main expects
log/log1.txt -- you'll need to reconcile the two (e.g. by numbering the log
file, as chapter12-systemstate does) before replay will find it.
