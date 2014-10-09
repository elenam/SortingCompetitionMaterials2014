
### the script for running the 2014 sorting competition 

### to pin to one core, run as: taskset -c 0 ruby run_all.rb

groups = 15 # number of groups (including group 0 which is the sample sorting)
loops = [20, 10, 20]
runs = 3
elements = [10000, 50000, 10000]
lambdas = [1.2, 2.8, 7.0] 
inFileNames = ["in1.txt", "in2.txt", "in3.txt"]

# loop over lambdas and elements

lambdas.length.times do |r| 
  # generate data, store it in a file
  resultsFile = "results#{r + 1}.txt"
  system("java DataGenerator #{inFileNames[r]} #{elements[r]} #{lambdas[r]}")
  system("java DataGenerator #{inFileNames[r]} #{elements[r]} #{lambdas[r]} >> #{resultsFile}")
 
  # run all groups
  groups.times do |j|
    system("echo 'Group#{j}\n'")
    system("echo '\n Group#{j}: \n' >> #{resultsFile}")
    runs.times do 
      system("java Group#{j} #{inFileNames[r]} outRun#{r}Group#{j}.txt #{loops[r]} >> #{resultsFile}") 
    end
    system("echo 'Results of diff:\n' >> #{resultsFile}")
    system("diff --ignore-all-space --brief outRun#{r}Group#{j}.txt outRun#{r}Group0.txt >> #{resultsFile}")
  end
end


