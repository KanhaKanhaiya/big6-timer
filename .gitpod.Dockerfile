FROM gitpod/workspace-full-vnc:latest

RUN cd ~
RUN sudo apt install gh
RUN echo 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 | gh auth login --with-token
RUN gh release download --repo KanhaKanhaiya/study-management-private 0.0.2
RUN tar xf AndroidStudioFiles.tar.gz
RUN cp Home*/* . -r

