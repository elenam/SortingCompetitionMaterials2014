
### the script for running the 2014 sorting competition  

groups = 15 # number of groups (group 0 is the sample sorting)
loops = [20, 10, 20]
runs = 3
elements = [10000, 50000, 10000]
lambdas = [1.0, 2.0, 7.0] 
inFileNames = ["in1.txt", "in2.txt", "in3.txt"]

# loop over lambdas and elements

lambdas.length.times do |r| 
  # generate data, store it in a file
  system("java DataGenerator #{inFileNames[r]} #{elements[r]} #{lambdas[r]}")
  # some printing here and in results....

  # run all groups
  groups.times do |j|
    system("echo 'Group#{j}\n'")
    system("echo '\n Group#{j}: \n' >> results.txt")
    runs.times do 
      system("java Group#{j} #{inFileNames[r]} outRun#{r}Group#{j}.txt #{loops[r]} >> results.txt") 
    end
    system("echo 'Results of diff:\n' >> results.txt")
    system("diff --ignore-all-space --brief outRun#{r}Group#{j}.txt outRun#{r}Group0.txt >> results.txt")
    # keep the output file this time:     
    #  system("rm out#{j}.txt")       
  end
end


